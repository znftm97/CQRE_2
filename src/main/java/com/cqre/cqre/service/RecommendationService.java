package com.cqre.cqre.service;

import com.cqre.cqre.domain.User;
import com.cqre.cqre.domain.post.Post;
import com.cqre.cqre.domain.post.Recommendation;
import com.cqre.cqre.dto.post.RecommendationResponseDto;
import com.cqre.cqre.exception.customexception.post.CPostNotFoundException;
import com.cqre.cqre.repository.RecommendationRepository;
import com.cqre.cqre.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Transactional
    public RecommendationResponseDto recommendation(Long postId){
        Post findPost = postRepository.CFindByPostId(postId).orElseThrow(CPostNotFoundException::new);
        User loginUser = userService.getLoginUser();
        Recommendation findRecommendation = recommendationRepository.CFindRecommendationByPostIdAndUserId(findPost.getId(), loginUser.getId());

        if(findRecommendation == null){
            findPost.addRecommendation();

            /*Recommendation recommendation = Recommendation.builder()
                                                            .post(findPost)
                                                            .user(loginUser)
                                                            .build();*/

            Recommendation recommendation = Recommendation.createRecommendation(loginUser, findPost);

            recommendationRepository.save(recommendation);
            return modelMapper.map(recommendation, RecommendationResponseDto.class);

        }else {
            findPost.subtractRecommendation();
            recommendationRepository.delete(findRecommendation);
        }

        return new RecommendationResponseDto();
    }

    public String recommendationCheck(Long postId){
        User loginUser = userService.getLoginUser();

        Recommendation recommendation = recommendationRepository.CFindRecommendationByPostIdAndUserId(postId, loginUser.getId());
        if (recommendation == null) {
            return "notExist";
        }else{
            return "exist";
        }
    }
}
