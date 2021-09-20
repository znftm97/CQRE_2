package com.cqre.cqre.dto.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FindItemImageDistinctDto {

    private Long bundleId;
    private Long id;

    @QueryProjection
    public FindItemImageDistinctDto(Long bundleId, Long id) {
        this.bundleId = bundleId;
        this.id = id;
    }
}
