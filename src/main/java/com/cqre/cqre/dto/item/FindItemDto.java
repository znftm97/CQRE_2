package com.cqre.cqre.dto.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FindItemDto {

    private Long id;
    private String name;
    private String itemExplanation;
    private int price;
    private String filePath;
    private Long bundleId;

    @QueryProjection
    public FindItemDto(Long id, String name, String itemExplanation, int price, String filePath, Long bundleId) {
        this.id = id;
        this.name = name;
        this.itemExplanation = itemExplanation;
        this.price = price;
        this.filePath = filePath;
        this.bundleId = bundleId;
    }
}
