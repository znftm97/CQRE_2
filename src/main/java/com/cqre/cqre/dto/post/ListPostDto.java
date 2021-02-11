package com.cqre.cqre.dto.post;

import com.cqre.cqre.entity.post.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ListPostDto {

    private Long id;
    private String title;
    private String content;
    private int postViews;
    private int recommendation;
    private LocalDateTime lastModifiedDate;

    public ListPostDto() {

    }

    public ListPostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postViews = post.getPostViews();
        this.recommendation = post.getRecommendation();
        this.lastModifiedDate = post.getLastModifiedDate();
    }
}
