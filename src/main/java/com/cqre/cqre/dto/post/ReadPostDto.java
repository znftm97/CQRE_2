package com.cqre.cqre.dto.post;

import com.cqre.cqre.domain.board.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReadPostDto {

    private Long id;
    private String title;
    private String content;
    private int postViews;
    private int recommendation;
    private LocalDateTime createDate;
    private String userName;
    private boolean AuthorCheck;

    public ReadPostDto(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postViews = post.getPostViews();
        this.recommendation = post.getRecommendation();
        this.createDate = post.getCreateDate();
        this.userName = post.getUser().getName();
    }
}
