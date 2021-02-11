package com.cqre.cqre.dto.post;

import com.cqre.cqre.entity.post.Board;
import lombok.Data;

@Data
public class CreatePostDto {

    private String title;
    private String content;

    public CreatePostDto() {

    }

    public CreatePostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
