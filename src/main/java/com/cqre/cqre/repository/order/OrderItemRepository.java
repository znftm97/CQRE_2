package com.cqre.cqre.repository.order;

import com.cqre.cqre.entity.shop.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "select o from OrderItem o join fetch o.item where o.order.user.id = :userId", countQuery = "select count(o.id) from OrderItem o where o.order.user.id = :userId")
    Page<OrderItem> findOrderItemByUserId(@Param("userId") Long userId, Pageable pageable);
}
