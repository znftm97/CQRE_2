package com.cqre.cqre.service;

import com.cqre.cqre.dto.post.PostFileDto;
import com.cqre.cqre.entity.post.Post;
import com.cqre.cqre.entity.post.PostFile;
import com.cqre.cqre.exception.customexception.post.CPostNotFoundException;
import com.cqre.cqre.repository.PostFileRepository;
import com.cqre.cqre.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostFileService {

    private final PostFileRepository postFileRepository;
    private final PostRepository postRepository;

    @Transactional
    public void saveFile(List<MultipartFile> files, Long postId){

        Post findPost = postRepository.findById(postId).orElseThrow(CPostNotFoundException::new);

        /*실행되는 위치 즉 프로젝트 폴더에 files 폴더에 파일 저장됨*/
        String savePath = "C:\\Users\\leejihoon\\Desktop\\CQRE\\files\\postFiles";

        /*파일 저장되는 폴더 없으면 생성*/
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
                String origFilename = files.get(i).getOriginalFilename(); /*원본 파일 명*/
                String filename = System.currentTimeMillis() + " - " + origFilename; /*파일 이름 중복되지 않도록*/
                String filePath = savePath + "\\" + filename;

                files.get(i).transferTo(new java.io.File(filePath));

                /*파일 생성*/
                PostFile postFile = PostFile.builder()
                        .originFilename(origFilename)
                        .filename(filename)
                        .filePath(filePath)
                        .post(findPost)
                        .build();

                /*파일 저장*/
                postFileRepository.save(postFile);

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*파일 다운로드*/
    public ResponseEntity<Resource> PostFileDownload(Long postFileId){
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
    }

    /*파일 읽기*/
    public List<PostFileDto> readPostFiles(Long postId) {
        List<PostFile> postFiles = postFileRepository.findPostFileByPostId(postId);
        List<PostFileDto> postFileDtos = postFiles.stream()
                                                    .map(p -> new PostFileDto(p))
                                                    .collect(Collectors.toList());

        return postFileDtos;
    }

}

