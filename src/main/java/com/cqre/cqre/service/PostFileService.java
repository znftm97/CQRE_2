package com.cqre.cqre.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cqre.cqre.dto.post.PostFileDto;
import com.cqre.cqre.domain.board.Post;
import com.cqre.cqre.domain.board.PostFile;
import com.cqre.cqre.exception.customexception.post.CPostNotFoundException;
import com.cqre.cqre.repository.PostFileRepository;
import com.cqre.cqre.repository.post.PostRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostFileService {

    private final PostFileRepository postFileRepository;
    private final PostRepository postRepository;

    private AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setAmazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        amazonS3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    public void upload(List<MultipartFile> multipartFiles, String dirName, Long postId) throws IOException {
        Post findPost = postRepository.findById(postId).orElseThrow(CPostNotFoundException::new);

        List<File> convertFiles = convert(multipartFiles);
        List<String> uploadImageUrls = uploadToS3(convertFiles, dirName);

        for (int i = 0; i < multipartFiles.size(); i++) {
            String origFilename = multipartFiles.get(i).getOriginalFilename(); /*원본 파일 명*/
            String filename = System.currentTimeMillis() + "_" + origFilename; /*파일 이름 중복되지 않도록*/
            String filePath = uploadImageUrls.get(i);

            PostFile postFile = PostFile.builder()
                    .post(findPost)
                    .originFilename(origFilename)
                    .filename(filename)
                    .filePath(filePath)
                    .build();

            postFileRepository.save(postFile);
        }
    }

    private List<String> uploadToS3(List<File> uploadFile, String dirName) {
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
        String home = System.getProperty("user.home");

        for (int i = 0; i < multipartFiles.size(); i++) {
            File convertFile = new File(home + File.separator + System.currentTimeMillis() + "_" + multipartFiles.get(i).getOriginalFilename());
            if (convertFile.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(multipartFiles.get(i).getBytes());
                }
                files.add(convertFile);
            }
        }

        return files;
    }

    /*파일 읽기*/
    public List<PostFileDto> readPostFiles(Long postId) {
        List<PostFile> postFiles = postFileRepository.findPostFileByPostId(postId);
        List<PostFileDto> postFileDtos = postFiles.stream()
                .map(p -> new PostFileDto(p))
                .collect(Collectors.toList());

        return postFileDtos;
    }

    /*파일 다운로드*/
   /* public ResponseEntity<Resource> PostFileDownload(Long postFileId){
        PostFile findPostFile = postFileRepository.findById(postFileId).get();

        Path path = Paths.get(findPostFile.getFilePath());
        try {
            Resource resource = new InputStreamResource(Files.newInputStream(path));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + findPostFile.getOriginFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    /*파일 업로드*//*
    @Transactional
    public void saveFile(List<MultipartFile> files, Long postId){

        Post findPost = postRepository.findById(postId).orElseThrow(CPostNotFoundException::new);

        *//*실행되는 위치 즉 프로젝트 폴더에 files 폴더에 파일 저장됨*//*
        String savePath = "C:\\Users\\leejihoon\\Desktop\\CQRE\\files\\postFiles";

        *//*파일 저장되는 폴더 없으면 생성*//*
        if (!new java.io.File(savePath).exists()) {
            try{
                new java.io.File(savePath).mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }

        for(int i=0; i<files.size(); i++){
            try {
                String origFilename = files.get(i).getOriginalFilename(); *//*원본 파일 명*//*
                String filename = System.currentTimeMillis() + " - " + origFilename; *//*파일 이름 중복되지 않도록*//*
                String filePath = savePath + "\\" + filename;

                files.get(i).transferTo(new java.io.File(filePath));

                *//*파일 생성*//*
                PostFile postFile = PostFile.builder()
                        .originFilename(origFilename)
                        .filename(filename)
                        .filePath(filePath)
                        .post(findPost)
                        .build();

                *//*파일 저장*//*
                postFileRepository.save(postFile);

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }*/

}

