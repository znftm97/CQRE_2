package com.cqre.cqre.repository.comment;

import com.cqre.cqre.domain.board.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom{

    @Query("select c from Comment c where c.post.id = :postId")
    List<Comment> findCommentByPostId(@Param("postId") Long postId);

    @Query("select c.id from Comment c where c.post.id = :postId")
    List<Long> findCommentIdsByPostId(@Param("postId") Long postId);

    @Query("delete from Comment c where c.id in :ids")
    @Modifying(clearAutomatically = true)
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);

    @Query(value = "select c from Comment c join fetch c.post where c.user.id = :userId and c.existsCheck = :existsCheck order by c.createDate desc",
            countQuery = "select count(c.id) from Comment c where c.user.id = :userId and c.existsCheck = :existsCheck")
    Page<Comment> findCommentByUserId(@Param("userId") Long userId, @Param("existsCheck") boolean existsCheck ,Pageable pageable);
}
