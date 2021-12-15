package com.cqre.cqre.dto.item;

import com.cqre.cqre.domain.shop.item.Item;
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
    private int stock;
    private String category;

    public FindItemDetailDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.itemExplanation = item.getItemExplanation();
        this.price = item.getPrice();
        this.createDate = item.getCreateDate();
        this.gender = String.valueOf(item.getGender());
        this.stock = item.getStock();
        this.category = item.getCategory().getName();
    }
}
