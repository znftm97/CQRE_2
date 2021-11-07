package com.cqre.cqre.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
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

@Service
public class FileUploadService {

    private AmazonS3 amazonS3Client;

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

    public List<String> uploadToS3(List<File> uploadFile, String dirName) {
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

    /*s3에 이미지 업로드*/
    public String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    /*MultipartFile을 File로 변환하면서 생긴 로컬에 생성된 File 삭제*/
    public void removeNewFile(List<File> targetFile) {
        for (int i = 0; i < targetFile.size(); i++) {
            if (targetFile.get(i).delete()) {
                return;
            }
        }
    }

    /*MultipartFile을 File로 변환 (s3에는 MultipartFile 타입 전송 불가능 하기 때문)*/
    public List<File> convert(List<MultipartFile> multipartFiles) throws IOException {
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
}
