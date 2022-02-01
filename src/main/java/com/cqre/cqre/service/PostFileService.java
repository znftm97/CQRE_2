package com.cqre.cqre.service;

import com.cqre.cqre.domain.board.Post;
import com.cqre.cqre.domain.board.PostFile;
import com.cqre.cqre.dto.post.PostFileDto;
import com.cqre.cqre.exception.customexception.post.CPostNotFoundException;
import com.cqre.cqre.repository.PostFileRepository;
import com.cqre.cqre.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostFileService {

    private final PostFileRepository postFileRepository;
    private final PostRepository postRepository;
    private final FileUploadService fileUploadService;

    public void upload(List<MultipartFile> multipartFiles, String dirName, Long postId) throws IOException {
        Post findPost = postRepository.findById(postId).orElseThrow(CPostNotFoundException::new);

        List<File> convertFiles = fileUploadService.convert(multipartFiles);
        List<String> uploadImageUrls = fileUploadService.uploadToS3(convertFiles, dirName);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < multipartFiles.size(); i++) {
            String origFilename = multipartFiles.get(i).getOriginalFilename();
            String filename = sb.append(System.currentTimeMillis()).append("_").append(origFilename).toString();
            String filePath = uploadImageUrls.get(i);

            postFileRepository.save(PostFile.of(origFilename, filename, filePath, findPost));
            sb.setLength(0);
        }
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

