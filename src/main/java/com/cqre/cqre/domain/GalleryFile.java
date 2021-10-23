package com.cqre.cqre.domain;

import com.cqre.cqre.domain.user.User;
import lombok.*;

import javax.persistence.*;

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
}
