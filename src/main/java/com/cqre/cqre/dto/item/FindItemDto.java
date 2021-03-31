package com.cqre.cqre.dto.item;

import com.cqre.cqre.entity.shop.ItemImage;
import com.cqre.cqre.entity.shop.item.Item;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FindItemDto {

    private Long id;
    private String name;
    private String itemExplanation;
    private int price;
    private String filename;
    private Long bundleId;
    private Long categoryId;

    @QueryProjection
    public FindItemDto(Long id, String name, String itemExplanation, int price, String filename, Long bundleId, Long categoryId) {
        this.id = id;
        this.name = name;
        this.itemExplanation = itemExplanation;
        this.price = price;
        this.filename = filename;
        this.bundleId = bundleId;
        this.categoryId = categoryId;
    }
}
