package com.cqre.cqre.dto.comment;

import com.cqre.cqre.domain.post.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReadCommentDto {
    private Long id;
    private String content;
    private String username;
    private LocalDateTime lastModifiedDate;

    public ReadCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUser().getName();
        this.lastModifiedDate = comment.getLastModifiedDate();
    }
}
