package com.cqre.cqre.dto.order;

import com.cqre.cqre.entity.shop.OrderItem;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindOrderItemDto {

    private String itemName;
    private int orderPrice;
    private int orderCount;
    private LocalDateTime createDate;

    public FindOrderItemDto(OrderItem orderItem) {
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getOrderPrice();
        this.orderCount = orderItem.getCount();
        this.createDate = orderItem.getCreateDate();
    }
}
