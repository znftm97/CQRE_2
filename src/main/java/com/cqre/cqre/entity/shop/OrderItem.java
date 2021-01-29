package com.cqre.cqre.entity.shop;

import com.cqre.cqre.entity.BaseEntity;
import com.cqre.cqre.entity.shop.item.Item;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "orderitem_id")
    private Long id;

    private int orderPrice;

    private int count;

    @OneToMany
    @JoinColumn(name = "item_id")
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
