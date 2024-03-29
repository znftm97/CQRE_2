package com.cqre.cqre.service;

import com.cqre.cqre.domain.board.Post;
import com.cqre.cqre.domain.board.Recommendation;
import com.cqre.cqre.domain.user.User;
import com.cqre.cqre.exception.customexception.post.CPostNotFoundException;
import com.cqre.cqre.repository.RecommendationRepository;
import com.cqre.cqre.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public void recommendation(Long postId){
        Post findPost = postRepository.CFindByPostId(postId).orElseThrow(CPostNotFoundException::new);
        User loginUser = userService.getLoginUser();
        Recommendation findRecommendation = recommendationRepository.CFindRecommendationByPostIdAndUserId(findPost.getId(), loginUser.getId());

        if(findRecommendation == null){
            findPost.addRecommendation();
            Recommendation recommendation = Recommendation.of(loginUser);
            recommendation.setPost(findPost);
            recommendationRepository.save(recommendation);
        }else {
            findPost.subtractRecommendation();
            recommendationRepository.delete(findRecommendation);
        }
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
