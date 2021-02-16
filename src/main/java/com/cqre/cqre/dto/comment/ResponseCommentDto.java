package com.cqre.cqre.dto.comment;

import com.cqre.cqre.entity.post.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseCommentDto {
    private Long id;
    private String username;
    private LocalDateTime lastModifiedDate;
    private String content;

    public ResponseCommentDto(Comment comment) {
        this.id = comment.getId();
        this.lastModifiedDate = comment.getLastModifiedDate();
        this.content = comment.getContent();
    }

}
