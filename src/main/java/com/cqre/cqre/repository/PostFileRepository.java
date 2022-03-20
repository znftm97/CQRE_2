package com.cqre.cqre.repository;

import com.cqre.cqre.domain.board.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostFileRepository extends JpaRepository<PostFile, Long> {

    @Query("select f from PostFile f where f.post.id = :postId")
    List<PostFile> findPostFileByPostId(@Param("postId") Long postId);

    @Query("select f.id from PostFile f where f.post.id = :postId")
    List<Long> findPostFileIdsByPostId(@Param("postId") Long postId);

    @Query("delete from PostFile f where f.id in :ids")
    @Modifying(clearAutomatically = true)
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}
