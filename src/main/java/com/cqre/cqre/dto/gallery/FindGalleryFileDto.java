package com.cqre.cqre.dto.gallery;

import com.cqre.cqre.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindGalleryFileDto {
    private Long id;
    private String title;
    private String filename;
    private LocalDateTime createDate;
    private String username;
    private Long bundleId;
    private Long bundleOrder;

    @QueryProjection
    public FindGalleryFileDto(Long id, String title, String filename, LocalDateTime createDate, String username, Long bundleId, Long bundleOrder) {
        this.id = id;
        this.title = title;
        this.filename = filename;
        this.createDate = createDate;
        this.username = username;
        this.bundleId = bundleId;
        this.bundleOrder = bundleOrder;
    }
}
