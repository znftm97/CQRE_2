package com.cqre.cqre.post;

import com.cqre.cqre.domain.board.Recommendation;
import com.cqre.cqre.repository.RecommendationRepository;
import com.cqre.cqre.security.WithMockCustomUser;
import com.cqre.cqre.service.RecommendationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class RecommendationServiceTest {

    @Autowired
    RecommendationService recommendationService;

    @Autowired
    RecommendationRepository recommendationRepository;

    @Test
    @DisplayName("글을 추천할 수 있다.")
    @WithMockCustomUser
    public void recommendation(){
        recommendationService.recommendation(9L);

        Recommendation recommendation = recommendationRepository.CFindRecommendationByPostIdAndUserId(9L, 1L);
        assertThat(recommendation).isNotNull();
    }

    @Test
    @DisplayName("특정 회원이 특정 글에 추천을 눌렀다.")
    @WithMockCustomUser
    public void recommendationCheck1(){
        String isRecommendation = recommendationService.recommendationCheck(74L);
        assertThat("exist").isEqualTo(isRecommendation);
    }

    @Test
    @DisplayName("특정 회원이 특정 글에 추천을 누르지 않았다.")
    @WithMockCustomUser
    public void recommendationCheck2(){
        String isRecommendation = recommendationService.recommendationCheck(75L);
        assertThat("notExist").isEqualTo(isRecommendation);
    }

}
