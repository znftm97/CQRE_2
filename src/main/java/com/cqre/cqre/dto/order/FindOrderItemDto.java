package com.cqre.cqre.dto.order;

import com.cqre.cqre.domain.shop.OrderItem;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindOrderItemDto {

    private Long id;
    private String itemName;
    private int orderPrice;
    private int orderQuantity;
    private LocalDateTime createDate;

    public FindOrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getOrderPrice();
        this.orderQuantity = orderItem.getOrderQuantity();
        this.createDate = orderItem.getCreateDate();
    }
}
