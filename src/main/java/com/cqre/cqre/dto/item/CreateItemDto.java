package com.cqre.cqre.dto.item;

import lombok.Data;

@Data
public class CreateItemDto {

    private String itemName;
    private String itemExplanation;
    private int price;
    private int stockCount;
    private String categorySelect;
    private String gender;
}
