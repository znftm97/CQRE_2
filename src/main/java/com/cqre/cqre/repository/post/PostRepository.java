package com.cqre.cqre.repository.post;

import com.cqre.cqre.domain.board.Board;
import com.cqre.cqre.domain.board.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    @Query("select p from Post p join fetch p.user where p.id = :id")
    Optional<Post> CFindByPostId(@Param("id") Long id);

    /*정상 동작*/
    @Query(value = "select p from Post p join fetch p.user where p.board = :board", countQuery = "select count(p.id) from Post p where p.board = :board")
    Page<Post> findPostByBoard(@Param("board") Board board, Pageable pageable);

    @Query("select p from Post p where p.user.id = :userId order by p.createDate desc")
    Page<Post> findPostByUserId(@Param("userId") Long userId, Pageable pageable);

    /*에러*/
    /*@Query(value = "select p from Post p join fetch p.user where p.board = :board")
    Page<Post> findPostByBoard2(@Param("board") Board board, Pageable pageable);*/
}
