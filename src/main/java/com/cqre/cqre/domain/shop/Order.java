package com.cqre.cqre.domain.shop;

import com.cqre.cqre.domain.BaseEntity;
import com.cqre.cqre.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    @JoinColumn(name = "usercoupon_id")
    private UserCoupon coupon;

    @Builder
    public Order(OrderStatus status, User user, List<OrderItem> orderItems, UserCoupon coupon) {
        this.status = status;
        this.user = user;
        this.orderItems = orderItems;
        this.coupon = coupon;
    }

    /*주문 생성*/
    public static Order of(User user, OrderItem orderItem) {
        Order order = Order.builder()
                            .user(user)
                            .status(OrderStatus.ORDER)
                            .orderItems(new ArrayList<>())
                            .build();

        order.orderItems.add(orderItem);
        orderItem.setOrder(order);

        return order;
    }

    /*쿠폰같이 주문 생성*/
    public static Order createOrderWithCoupon(User user, OrderItem orderItem, UserCoupon coupon) {
        Order order = Order.builder()
                        .user(user)
                        .status(OrderStatus.ORDER)
                        .orderItems(new ArrayList<>())
                        .coupon(coupon)
                        .build();

        order.orderItems.add(orderItem);
        orderItem.setOrder(order);

        return order;
    }

    /*장바구니 생성*/
    public static Order createBasket(User user, OrderItem orderItem) {
        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.BASKET)
                .orderItems(new ArrayList<>())
                .build();

        order.orderItems.add(orderItem);
        orderItem.setOrder(order);

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
