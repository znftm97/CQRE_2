package com.cqre.cqre.repository.Item;

import com.cqre.cqre.domain.shop.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    @Query("select i from Item i join fetch i.category where i.id = :itemId")
    Item findItemsWithCategory(@Param("itemId") Long itemId);

    @Query("select i from Item i where i.id = :itemId")
    Item findItemById(@Param("itemId") Long itemId);
}
