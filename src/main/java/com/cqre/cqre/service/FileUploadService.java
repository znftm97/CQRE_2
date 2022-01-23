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
import java.util.stream.Collectors;

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

    @PostConstruct
    public void setAmazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        amazonS3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    public List<String> uploadToS3(List<File> uploadFile, String dirName) {
        return uploadFile.stream()
                .map(file -> putS3(file, dirName + "/" + System.currentTimeMillis() + "_" + file.getName()))
                .collect(Collectors.toList());
    }

    /*s3에 이미지 업로드*/
    public String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    /*MultipartFile을 File로 변환 (s3에는 MultipartFile 타입 전송 불가능 하기 때문)*/
    public List<File> convert(List<MultipartFile> multipartFiles) throws IOException {
        List<File> files = new ArrayList<>();
        String home = System.getProperty("user.home");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < multipartFiles.size(); i++) {
            String pathname = sb.append(home).append(File.separator).append(System.currentTimeMillis()).append("_")
                                .append(multipartFiles.get(i).getOriginalFilename()).toString();
            File convertFile = new File(pathname);
            if (convertFile.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(multipartFiles.get(i).getBytes());
                }
                files.add(convertFile);
            }
            sb.setLength(0);
        }

        return files;
    }
}
