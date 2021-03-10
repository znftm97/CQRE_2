package com.cqre.cqre.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FindGalleryFileDistinctDto {

    private Long bundleId;
    private Long id;

    @QueryProjection
    public FindGalleryFileDistinctDto(Long bundleId, Long id) {
        this.bundleId = bundleId;
        this.id = id;
    }
}
