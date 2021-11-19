package com.cqre.cqre.domain;

import com.cqre.cqre.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GalleryFile extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "galleryfile_id")
    private Long id;

    private String title;

    private String originFilename;

    private String filename;

    private String filePath;

    private String bundleId;

    private Long bundleOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public GalleryFile(String title, String originFilename, String filename, String filePath, String bundleId, Long bundleOrder, User user) {
        this.title = title;
        this.originFilename = originFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.bundleId = bundleId;
        this.bundleOrder = bundleOrder;
        this.user = user;
    }
}
