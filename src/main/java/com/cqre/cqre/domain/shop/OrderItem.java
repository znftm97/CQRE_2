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

    @Builder
    public OrderItem(int orderPrice, int count, Item item) {
        this.orderPrice = orderPrice;
        this.count = count;
        this.item = item;
    }

    @Id
    @GeneratedValue
    @Column(name = "orderitem_id")
    private Long id;

    private int orderPrice;

    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public static OrderItem of(Item item, int count) {
        return OrderItem.builder()
                    .item(item)
                    .orderPrice(item.getPrice()*count)
                    .count(count)
                    .build();
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
