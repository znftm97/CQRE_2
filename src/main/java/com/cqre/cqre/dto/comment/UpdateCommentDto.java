package com.cqre.cqre.dto.comment;

import lombok.Data;

@Data
public class UpdateCommentDto {
    private Long commentId;
    private String updateContent;
}
