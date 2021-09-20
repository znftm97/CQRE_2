package com.cqre.cqre.dto.post;

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
}
