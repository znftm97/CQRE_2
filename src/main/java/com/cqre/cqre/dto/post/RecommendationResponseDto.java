package com.cqre.cqre.dto.post;

import lombok.Data;

@Data
public class RecommendationResponseDto {

    private Long userId;
    private Long postId;
    private int checks;
}
