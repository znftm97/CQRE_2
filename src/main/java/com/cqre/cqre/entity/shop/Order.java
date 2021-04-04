package com.cqre.cqre.entity.shop;

import com.cqre.cqre.entity.BaseEntity;
import com.cqre.cqre.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    /*주문 생성*/
    public static Order createOrder(User user, OrderItem orderItem) {
        Order order = new Order();
        order.user = user;
        order.orderItems.add(orderItem);
        orderItem.setOrder(order);
        order.status = OrderStatus.ORDER;

        return order;
    }

    /*장바구니 생성*/
    public static Order createBasket(User user, OrderItem orderItem) {
        Order order = new Order();
        order.user = user;
        order.orderItems.add(orderItem);
        orderItem.setOrder(order);
        order.status = OrderStatus.BASKET;

        return order;
    }

    /*주문 취소*/
    public void cancelOrder() {
        this.status = OrderStatus.CANCEL;
    }

    /*재 주문, 장바구니 바로 주문*/
    public void reOrder() {
        this.status = OrderStatus.ORDER;
    }
}
