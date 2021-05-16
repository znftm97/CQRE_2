package com.cqre.cqre.entity;

import lombok.*;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GalleryFile extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "galleryfile_id")
    private Long id;

    private String title;

    private String originFilename;

    private String filename;

    private String filePath;

    private Long bundleId;

    private Long bundleOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setBundleId(AtomicLong bundleId) {
        this.bundleId = bundleId.getAndIncrement();
    }
}
