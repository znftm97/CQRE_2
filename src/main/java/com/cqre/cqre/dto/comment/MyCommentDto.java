package com.cqre.cqre.dto.comment;

import com.cqre.cqre.domain.post.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyCommentDto {

    private String content;
    private String title;
    private LocalDateTime createDate;

    public MyCommentDto(Comment comment) {
        this.content = comment.getContent();
        this.title = comment.getPost().getTitle();
        this.createDate = comment.getCreateDate();
    }
}
