package com.cqre.cqre.dto.post;

import com.cqre.cqre.domain.post.Post;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ListPostDto {

    private Long id;
    private String title;
    private String content;
    private int postViews;
    private int recommendation;
    private LocalDateTime createDate;
    private String name;

    public ListPostDto() {

    }

    public ListPostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postViews = post.getPostViews();
        this.recommendation = post.getRecommendation();
        this.createDate = post.getCreateDate();
        this.name = post.getUser().getName();
    }

    @QueryProjection
    public ListPostDto(Long id, String title, String content, int postViews, int recommendation, LocalDateTime createDate, String name) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postViews = postViews;
        this.recommendation = recommendation;
        this.createDate = createDate;
        this.name = name;
    }
}
