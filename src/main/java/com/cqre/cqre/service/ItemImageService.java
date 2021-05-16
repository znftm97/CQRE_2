package com.cqre.cqre.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.entity.shop.ItemImage;
import com.cqre.cqre.entity.shop.item.CommonItem;
import com.cqre.cqre.exception.customexception.CFileIsNotImage;
import com.cqre.cqre.repository.itemImage.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;
    private final UserService userService;

    private AmazonS3 amazonS3Client;

    @Value("${custom.path.shop-images}")
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

    public void upload1(List<MultipartFile> multipartFiles, String dirName, CommonItem commonItem) throws IOException {
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

            ItemImage itemImage = ItemImage.builder()
                    .originFilename(origFilename)
                    .filename(filename)
                    .filePath(filePath)
                    .bundleOrder(System.currentTimeMillis())
                    .item(commonItem)
                    .build();

            synchronized (bundleId) {
                itemImage.setBundleId(bundleId);
            }

            itemImageRepository.save(itemImage);
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

    /*상품 상세 이미지 조회*/
    public List<String> findItemImageDetail(Long bundleId) {
        List<ItemImage> findItemImages = itemImageRepository.findItemImageByBundleId(bundleId);
        return findItemImages.stream()
                .map(i -> i.getFilePath())
                .collect(Collectors.toList());
    }

    /*상품 이미지 생성*/
   /* @Transactional
    public void createItemImage(MultipartFile[] files, CommonItem commonItem) {

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

                ItemImage itemImage = ItemImage.builder()
                        .originFilename(origFilename)
                        .filename(filename)
                        .filePath(filePath)
                        .bundleOrder(System.currentTimeMillis())
                        .bundleId(bundleId.get())
                        .item(commonItemcommonItem)
                        .build();

                itemImageRepository.save(itemImage);

            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        bundleId.incrementAndGet();
    }*/
}
