package com.cqre.cqre.dto.gallery;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindGalleryFileDto {
    private Long id;
    private String title;
    private String filePath;
    private LocalDateTime createDate;
    private String username;
    private String bundleId;
    private Long bundleOrder;

    @QueryProjection
    public FindGalleryFileDto(Long id, String title, String filePath, LocalDateTime createDate, String username, String bundleId, Long bundleOrder) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.createDate = createDate;
        this.username = username;
        this.bundleId = bundleId;
        this.bundleOrder = bundleOrder;
    }
}
