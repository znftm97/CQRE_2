package com.cqre.cqre.controller;

import com.cqre.cqre.entity.post.PostFile;
import com.cqre.cqre.repository.PostFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
public class PostFileController {

    private final PostFileRepository postFileRepository;

    /*파일 다운로드*/
    @GetMapping("/postFile/download/{postFileId}")
    public ResponseEntity<Resource> PostFileDownload(@PathVariable("postFileId") Long postFileId){
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
}
