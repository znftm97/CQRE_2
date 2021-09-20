package com.cqre.cqre.dto.comment;

import lombok.Data;

@Data
public class CreateCommentDto {
    private String content;
    private Long postId;
}
