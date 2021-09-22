package com.cqre.cqre.repository.order;

import com.cqre.cqre.domain.shop.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
