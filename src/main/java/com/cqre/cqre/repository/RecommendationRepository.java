package com.cqre.cqre.repository;

import com.cqre.cqre.entity.post.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    @Query("select r from Recommendation r join fetch r.post where r.post.id = :postId and r.user.id = :userId")
    Recommendation CFindRecommendationByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);
}
