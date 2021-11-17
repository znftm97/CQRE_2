package com.cqre.cqre.controller;

import com.cqre.cqre.service.RecommendationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CREATED;

@Controller
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/recommendations")
    @ResponseBody
    @ResponseStatus(CREATED)
    public String createRecommendation(@RequestBody RecommendationRequestDto requestDto) {
        recommendationService.recommendation(requestDto.getPostId());
        return "recommendation";
    }

    @Data
    static class RecommendationRequestDto{
        private Long postId;
    }
}
