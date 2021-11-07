package com.cqre.cqre.domain.shop;

import com.cqre.cqre.domain.BaseEntity;
import com.cqre.cqre.domain.shop.item.Item;
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

    private String bundleId;

    private Long bundleOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
