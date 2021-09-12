package com.cqre.cqre.repository.comment;

import com.cqre.cqre.domain.QUser;
import com.cqre.cqre.domain.post.Comment;
import com.cqre.cqre.domain.post.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.cqre.cqre.domain.post.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findCommentAllByPost(Long postId) {
        return queryFactory
                .selectFrom(comment)
                .join(comment.post, QPost.post).fetchJoin()
                .join(comment.user, QUser.user).fetchJoin()
                .where(comment.post.id.eq(postId))
                .orderBy(comment.bundleId.asc())
                .orderBy(comment.bundleOrder.asc())
                .fetch();
    }
}
