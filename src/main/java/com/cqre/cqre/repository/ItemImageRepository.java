package com.cqre.cqre.repository;

import com.cqre.cqre.domain.shop.ItemImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    @Query("select i from ItemImage i where i.bundleId = :bundleId")
    List<ItemImage> findItemImageByBundleId(@Param("bundleId") Long bundleId);

    @Query("select i from ItemImage i where i.item.category.name = :categoryName")
    Page<ItemImage> findItemsByCategory(@Param("categoryName") String categoryName, Pageable pageable);
}
