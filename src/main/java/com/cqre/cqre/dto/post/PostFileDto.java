package com.cqre.cqre.dto.post;

import com.cqre.cqre.domain.post.PostFile;
import lombok.Data;

@Data
public class PostFileDto {
    private Long id;
    private String originFilename;
    private String filename;
    private String filePath;

    public PostFileDto(PostFile postFile) {
        this.id = postFile.getId();
        this.originFilename = postFile.getOriginFilename();
        this.filename = postFile.getFilename();
        this.filePath = postFile.getFilePath();
    }
}
