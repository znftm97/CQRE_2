package com.cqre.cqre.dto.post;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreatePostDto {

    @NotEmpty(message = "필수로 입력해야 합니다.")
    private String title;

    @NotEmpty(message = "필수로 입력해야 합니다.")
    private String content;

    public CreatePostDto() {

    }

    public CreatePostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
