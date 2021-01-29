package com.cqre.cqre.entity.post;

import com.cqre.cqre.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostFile extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "postfile_id")
    private Long id;

    private String originFilename;

    private String filename;

    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
