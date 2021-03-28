package com.cqre.cqre.entity.shop;

import com.cqre.cqre.entity.BaseEntity;
import com.cqre.cqre.entity.shop.item.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemImage extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "itemimage_id")
    private Long id;

    private String originFilename;

    private String filename;

    private String filePath;

    private Long bundleId;

    private Long bundleOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
