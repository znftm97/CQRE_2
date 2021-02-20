package com.cqre.cqre.dto.comment;

import lombok.Data;

@Data
public class RemoveCommentDto {
    private Long commentId;
    private Long postId;
}
