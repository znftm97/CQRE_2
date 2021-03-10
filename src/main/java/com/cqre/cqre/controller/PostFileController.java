package com.cqre.cqre.controller;

import com.cqre.cqre.service.PostFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PostFileController {

    private final PostFileService postFileService;

    /*파일 다운로드*/
    @GetMapping("/postFile/{postFileId}/download")
    public ResponseEntity<Resource> PostFileDownload(@PathVariable("postFileId") Long postFileId){
        return postFileService.PostFileDownload(postFileId);
    }
}
