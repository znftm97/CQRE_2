package com.cqre.cqre.repository;

import com.cqre.cqre.entity.post.Board;
import com.cqre.cqre.entity.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.user where p.id = :id")
    Optional<Post> CFindByPostId(@Param("id") Long id);

    @Query("select p from Post p where p.board = :board")
    Page<Post> findByPostAll(@Param("board") Board board, Pageable pageable);
}
