package com.cqre.cqre.dto.comment;

import com.cqre.cqre.domain.User;
import com.cqre.cqre.domain.post.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseCommentDto {
    private Long id;
    private String username;
    private LocalDateTime lastModifiedDate;
    private String content;
    private boolean authorCheck;
    private boolean existsCheck;
    private int depth;

    public ResponseCommentDto(Comment comment, User loginUser) {
        this.id = comment.getId();
        this.lastModifiedDate = comment.getLastModifiedDate();
        this.content = comment.getContent();
        this.username = comment.getUser().getName();
        this.existsCheck = comment.isExistsCheck();
        this.depth = comment.getDepth();

        if (comment.getUser().getId().equals(loginUser.getId())) {
            authorCheck = true;
        } else {
            authorCheck = false;
        }
    }
}
