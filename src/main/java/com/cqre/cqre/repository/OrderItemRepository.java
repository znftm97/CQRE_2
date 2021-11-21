package com.cqre.cqre.repository;

import com.cqre.cqre.domain.shop.OrderItem;
import com.cqre.cqre.domain.shop.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "select o from OrderItem o join fetch o.item where o.order.user.id = :userId and o.order.status = :orderStatus order by o.createDate desc",
            countQuery = "select count(o.id) from OrderItem o where o.order.user.id = :userId and o.order.status = :orderStatus")
    Page<OrderItem> findOrderItemByUserId(@Param("userId") Long userId, @Param("orderStatus") OrderStatus orderStatus, Pageable pageable);

    @Query("select o from OrderItem o join fetch o.order join fetch o.item where o.id = :orderItemId order by o.createDate desc")
    OrderItem findOrderItemWithOrder(@Param("orderItemId") Long orderItemId);

    @Query(value = "select o from OrderItem o join fetch o.item where o.order.user.id = :userId and o.order.status = :orderStatus order by o.createDate desc",
            countQuery = "select count(o.id) from OrderItem o where o.order.user.id = :userId and o.order.status = :orderStatus")
    Page<OrderItem> findOrderByOrderStatus(@Param("userId") Long userId, @Param("orderStatus") OrderStatus orderStatus, Pageable pageable);
}
