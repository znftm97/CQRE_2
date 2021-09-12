package com.cqre.cqre.repository;

import com.cqre.cqre.domain.post.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    @Query("select r from Recommendation r join fetch r.post where r.post.id = :postId and r.user.id = :userId")
    Recommendation CFindRecommendationByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);

    @Query("select r from Recommendation r where r.post.id = :postId")
    List<Recommendation> findRecommendationByPostId(@Param("postId") Long postId);

    @Query("delete from Recommendation r where r.id in :ids")
    @Modifying(clearAutomatically = true)
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}
