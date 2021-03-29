package com.cqre.cqre.dto.item;

import com.cqre.cqre.entity.shop.ItemImage;
import com.cqre.cqre.entity.shop.item.Item;
import com.cqre.cqre.entity.shop.item.ItemGender;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindItemDetailDto {

    private Long id;
    private String name;
    private String itemExplanation;
    private int price;
    private String filename;
    private LocalDateTime createDate;
    private String gender;
    private int stockCount;
    private String category;

    public FindItemDetailDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.itemExplanation = item.getItemExplanation();
        this.price = item.getPrice();
        this.createDate = item.getCreateDate();
        this.gender = String.valueOf(item.getGender());
        this.stockCount = item.getStockCount();
        this.category = item.getCategory().getName();
    }
}
