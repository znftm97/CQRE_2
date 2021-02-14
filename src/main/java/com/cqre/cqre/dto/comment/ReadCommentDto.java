package com.cqre.cqre.dto.comment;

import com.cqre.cqre.entity.post.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReadCommentDto {
    private String content;
    private String username;
    private LocalDateTime lastModifiedDate;

    public ReadCommentDto(Comment comment) {
        this.content = comment.getContent();
        this.username = comment.getUser().getName();
        this.lastModifiedDate = comment.getLastModifiedDate();
    }
}
