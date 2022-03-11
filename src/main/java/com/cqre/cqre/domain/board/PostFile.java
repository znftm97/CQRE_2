package com.cqre.cqre.domain.board;

import com.cqre.cqre.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
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

    @Builder
    public PostFile(String originFilename, String filename, String filePath){
        this.originFilename = originFilename;
        this.filename = filename;
        this.filePath = filePath;
    }

    public static PostFile of(String originFilename, String filename, String filePath){
        PostFile postFile = PostFile.builder()
                .originFilename(originFilename)
                .filename(filename)
                .filePath(filePath)
                .build();

        return postFile;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
