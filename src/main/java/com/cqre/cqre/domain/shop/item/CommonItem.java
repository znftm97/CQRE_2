package com.cqre.cqre.domain.shop.item;

import com.cqre.cqre.dto.item.CreateItemDto;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class CommonItem extends Item {

    public CommonItem createCommonItem(CreateItemDto dto, Category category){
        CommonItem commonItem = new CommonItem();

        commonItem.setCategory(category);
        commonItem.setItemExplanation(dto.getItemExplanation());
        commonItem.setName(dto.getItemName());
        commonItem.setPrice(dto.getPrice());
        commonItem.setStock(dto.getStock());

        if (dto.getGender().equals("MEN")) {
            commonItem.setGender(ItemGender.MEN);
        } else if (dto.getGender().equals("WOMEN")) {
            commonItem.setGender(ItemGender.WOMEN);
        } else {
            commonItem.setGender(ItemGender.PUBLIC);
        }

        return commonItem;
    }
}
