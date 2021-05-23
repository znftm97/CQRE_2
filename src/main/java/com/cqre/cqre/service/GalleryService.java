package com.cqre.cqre.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cqre.cqre.dto.gallery.CreateGalleryDto;
import com.cqre.cqre.dto.gallery.FindGalleryFileDetailDto;
import com.cqre.cqre.dto.gallery.FindGalleryFileDto;
import com.cqre.cqre.entity.GalleryFile;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.exception.customexception.CFileIsNotImage;
import com.cqre.cqre.repository.gallery.GalleryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GalleryService {

    private final UserService userService;
    private final GalleryRepository galleryRepository;

    private AmazonS3 amazonS3Client;

    @Value("${custom.path.gallery-images}")
    private String savePath;

    @Value("${custom.path.temp}")
    private String tempPath;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    private final AtomicLong bundleId = new AtomicLong(1);

    @PostConstruct
    public void setAmazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        amazonS3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    public void upload1(List<MultipartFile> multipartFiles, String dirName, String title) throws IOException {
        User loginUser = userService.getLoginUser();

        /*이미지 파일인지 검증*/
        for (MultipartFile uploadFile : multipartFiles) {
            if (uploadFile.getContentType().startsWith("image") == false) {
                throw new CFileIsNotImage();
            }
        }

        List<File> convertFiles = convert(multipartFiles);
        List<String> uploadImageUrls = upload2(convertFiles, dirName);

        for (int i = 0; i < multipartFiles.size(); i++) {
            String origFilename = multipartFiles.get(i).getOriginalFilename(); /*원본 파일 명*/
            String filename = System.currentTimeMillis() + "_" + origFilename; /*파일 이름 중복되지 않도록*/
            String filePath = uploadImageUrls.get(i);

            GalleryFile galleryFile = GalleryFile.builder()
                    .title(title)
                    .filename(filename)
                    .filePath(filePath)
                    .originFilename(origFilename)
                    .user(loginUser)
                    .bundleId(bundleId.get())
                    .bundleOrder(System.currentTimeMillis())
                    .build();

            galleryRepository.save(galleryFile);
        }

        bundleId.incrementAndGet();
    }

    private List<String> upload2(List<File> uploadFile, String dirName) {
        String fileName = "";
        String uploadImageUrl = "";
        List<String> uploadImageUrls = new ArrayList<>();

        for (int i = 0; i < uploadFile.size(); i++) {
             fileName = dirName + "/" + System.currentTimeMillis() + "_" + uploadFile.get(i).getName(); // 파일명/랜덤숫자_파일이름
             uploadImageUrl = putS3(uploadFile.get(i), fileName);

             uploadImageUrls.add(uploadImageUrl);
             removeNewFile(uploadFile);
        }

        return uploadImageUrls;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(List<File> targetFile) {
        for (int i = 0; i < targetFile.size(); i++) {
            if (targetFile.get(i).delete()) {
                return;
            }
            log.info("임시 파일이 삭제 되지 못했습니다. 파일 이름: {}", targetFile.get(i).getName());
        }
    }


    private List<File> convert(List<MultipartFile> multipartFiles) throws IOException {
        List<File> files = new ArrayList<>();

        for (int i = 0; i < multipartFiles.size(); i++) {
            File convertFile = new File(tempPath + System.currentTimeMillis() + "_" + multipartFiles.get(i).getOriginalFilename());
            if (convertFile.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(multipartFiles.get(i).getBytes());
                }
                files.add(convertFile);
            }
        }

        return files;
    }

    /*갤러리 파일 중복 제거 조회*/
    public Page<FindGalleryFileDto> findGalleryFilesDistinct(Pageable pageable){
        return galleryRepository.findAllDistinctByBundleId(pageable);
    }

    /*갤러리 상세 조회 페이지*/
    public Page<FindGalleryFileDetailDto> findGalleryFiles(Pageable pageable, Long bundleId){
        Page<GalleryFile> findGalleryFiles = galleryRepository.findGalleryFileByBundleIdPaging(bundleId, pageable);

        Long loginUserId = userService.getLoginUser().getId();
        Long galleryFileCreatorUserId = findGalleryFiles.getContent().get(0).getUser().getId();

        if (loginUserId.equals(galleryFileCreatorUserId)) {
            return findGalleryFiles.map(g -> new FindGalleryFileDetailDto(g, "true"));
        }else {
            return findGalleryFiles.map(g -> new FindGalleryFileDetailDto(g, "false"));
        }
    }

    /*삭제*/
    @Transactional
    public void galleryFileDelete(Long bundleId){
        List<GalleryFile> findGalleryFiles = galleryRepository.findGalleryFileByBundleId(bundleId);

        List<Long> GalleryFileIds = findGalleryFiles.stream()
                .map(g -> g.getId())
                .collect(Collectors.toList());

        galleryRepository.deleteAllByIdInQuery(GalleryFileIds);
    }

    /*갤러리 파일 생성*/
    /*@Transactional
    public void createGallery(MultipartFile[] files, CreateGalleryDto createGalleryDto) {
        User loginUser = userService.getLoginUser();

        if (!new java.io.File(savePath).exists()) {
            try{
                new java.io.File(savePath).mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }

        *//*이미지 파일인지 검사*//*
        for (MultipartFile uploadFile : files) {
            if (uploadFile.getContentType().startsWith("image") == false) {
                throw new CFileIsNotImage();
            }
        }

        for(int i=0; i<files.length; i++){
            try {
                String origFilename = files[i].getOriginalFilename(); *//*원본 파일 명*//*
                String filename = System.currentTimeMillis() + "_" + origFilename; *//*파일 이름 중복되지 않도록*//*
                String filePath = savePath + "\\" + filename;

                files[i].transferTo(new java.io.File(filePath));

                GalleryFile galleryFile = GalleryFile.builder()
                        .title(createGalleryDto.getTitle())
                        .filename(filename)
                        .filePath(filePath)
                        .originFilename(origFilename)
                        .user(loginUser)
                        .bundleId(bundleId.get())
                        .bundleOrder(System.currentTimeMillis())
                        .build();

                galleryRepository.save(galleryFile);

            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        bundleId.incrementAndGet();
    }*/
}
