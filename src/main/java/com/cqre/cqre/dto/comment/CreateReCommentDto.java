package com.cqre.cqre.dto.comment;

import lombok.Data;

@Data
public class CreateReCommentDto {
    private String content;
    private Long postId;
    private Long originalCommentId;
}
