package com.cqre.cqre.dto.order;

import com.cqre.cqre.entity.shop.OrderItem;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindOrderItemDto {

    private Long id;
    private String itemName;
    private int orderPrice;
    private int orderCount;
    private LocalDateTime createDate;

    public FindOrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getOrderPrice();
        this.orderCount = orderItem.getCount();
        this.createDate = orderItem.getCreateDate();
    }
}
