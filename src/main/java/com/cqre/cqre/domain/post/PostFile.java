package com.cqre.cqre.domain.post;

import com.cqre.cqre.domain.BaseEntity;
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

    //== 연관관계 편의 메서드 ==//
    public void setPost(Post post) {
        this.post = post;
        post.getPostFiles().add(this);
    }

    @Builder
    public PostFile(String originFilename, String filename, String filePath, Post post){
        this.originFilename = originFilename;
        this.filename = filename;
        this.filePath = filePath;
        setPost(post);
    }
}
