package com.cqre.cqre.entity;

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
    @Column(name = "glleryfile_id")
    private Long id;

    private String title;

    private String originFilename;

    private String filename;

    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public GalleryFile(String title, String originFilename, String filename, String filePath, User user){
        this.title = title;
        this.originFilename = originFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.user = user;
    }

}
