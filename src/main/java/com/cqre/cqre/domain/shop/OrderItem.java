package com.cqre.cqre.domain.shop;

import com.cqre.cqre.domain.BaseEntity;
import com.cqre.cqre.domain.shop.item.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "orderitem_id")
    private Long id;

    private int orderPrice;

    private int orderQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public OrderItem(int orderPrice, int orderQuantity, Item item) {
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
        this.item = item;
    }

    public static OrderItem of(Item item, int orderQuantity) {
        return OrderItem.builder()
                    .item(item)
                    .orderPrice(item.getPrice()*orderQuantity)
                    .orderQuantity(orderQuantity)
                    .build();
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void calculateDiscountPrice(Item item, Long discountRate){
        int discountPrice = (item.getPrice() * orderQuantity) * discountRate.intValue() / 100;
        this.orderPrice = (item.getPrice() * orderQuantity) - discountPrice;
    }

}
