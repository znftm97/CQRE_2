package com.cqre.cqre.dto.gallery;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FindGalleryFileDistinctDto {

    private String bundleId;
    private Long id;

    @QueryProjection
    public FindGalleryFileDistinctDto(String bundleId, Long id) {
        this.bundleId = bundleId;
        this.id = id;
    }
}
