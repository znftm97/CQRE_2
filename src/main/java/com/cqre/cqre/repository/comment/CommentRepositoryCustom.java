package com.cqre.cqre.repository.comment;

import com.cqre.cqre.domain.board.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findCommentAllByPost(Long postId);
}
