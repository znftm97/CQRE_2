package com.cqre.cqre.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReCommentDto {
    private String content;
    private Long postId;
    private Long originalCommentId;
}
