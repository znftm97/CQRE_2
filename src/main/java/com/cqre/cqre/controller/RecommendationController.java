package com.cqre.cqre.controller;

import com.cqre.cqre.dto.post.RecommendationResponseDto;
import com.cqre.cqre.service.RecommendationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/recommendation")
    @ResponseBody
    public RecommendationResponseDto recommendation(@RequestBody RecommendationRequestDto requestDto) {
        return recommendationService.recommendation(requestDto.getPostId());
    }

    @Data
    static class RecommendationRequestDto{
        private Long postId;
    }
}
