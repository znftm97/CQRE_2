package com.cqre.cqre.dto.post;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ReadPostWithDto {

    private Long id; // postId
    private String title;
    private String content;
    private int postViews;
    private int recommendation;
    private LocalDateTime createDate;
    private String userName;
    private boolean AuthorCheck;
    private String originFilename;
    private String filePath;
    private int recommendationCheck;

    @QueryProjection
    public ReadPostWithDto(Long id, String title, String content, int postViews, int recommendation, LocalDateTime createDate, String userName, String originFilename, String filePath, int recommendationCheck) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postViews = postViews;
        this.recommendation = recommendation;
        this.createDate = createDate;
        this.userName = userName;
        this.originFilename = originFilename;
        this.filePath = filePath;
    }
}
