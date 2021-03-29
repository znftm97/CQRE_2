package com.cqre.cqre.repository.itemImage;

import com.cqre.cqre.entity.shop.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    @Query("select i from ItemImage i where i.bundleId = :bundleId")
    List<ItemImage> findItemImageByBundleId(@Param("bundleId") Long bundleId);
}
