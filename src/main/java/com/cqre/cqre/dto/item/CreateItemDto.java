package com.cqre.cqre.dto.item;

import lombok.Data;

@Data
public class CreateItemDto {

    private String itemName;
    private String itemExplanation;
    private int price;
    private int stock;
    private String categorySelect;
    private String gender;

    public CreateItemDto(String itemName, String itemExplanation, int price, int stock, String categorySelect, String gender) {
        this.itemName = itemName;
        this.itemExplanation = itemExplanation;
        this.price = price;
        this.stock = stock;
        this.categorySelect = categorySelect;
        this.gender = gender;
    }
}
