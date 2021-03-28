package com.cqre.cqre.repository.Item;

import com.cqre.cqre.entity.shop.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
}
