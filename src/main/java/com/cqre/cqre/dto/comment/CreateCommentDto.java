package com.cqre.cqre.dto.comment;

import com.cqre.cqre.domain.board.Comment;
import com.cqre.cqre.domain.board.Post;
import com.cqre.cqre.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class CreateCommentDto {
    private String content;
    private Long postId;

    public Comment toEntity(Post findPost, User loginUser, AtomicLong bundleId){
        return Comment.builder()
                .content(this.content)
                .user(loginUser)
                .post(findPost)
                .depth(1)
                .bundleId(bundleId.getAndIncrement())
                .bundleOrder(System.currentTimeMillis())
                .existsCheck(true)
                .build();
    }
}