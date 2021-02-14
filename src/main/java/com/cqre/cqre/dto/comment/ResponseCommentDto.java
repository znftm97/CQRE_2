package com.cqre.cqre.dto.comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseCommentDto {
    private String username;
    private LocalDateTime lastModifiedDate;
    private String content;

    public ResponseCommentDto(String username, LocalDateTime lastModifiedDate, String content) {
        this.username = username;
        this.lastModifiedDate = lastModifiedDate;
        this.content = content;
    }
}
