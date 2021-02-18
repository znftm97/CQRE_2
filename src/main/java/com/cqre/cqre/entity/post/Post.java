package com.cqre.cqre.entity.post;

import com.cqre.cqre.dto.post.CreateUpdatePostDto;
import com.cqre.cqre.entity.BaseEntity;
import com.cqre.cqre.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private int postViews;

    private int recommendation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Board board;

    /*조회수 증가*/
    public void addPostViews(){
        this.postViews++;
    }

    /*추천수 증가*/
    public void addRecommendation(){
        this.recommendation++;
    }

    /*추천수 감소*/
    public void subtractRecommendation(){
        this.recommendation--;
    }

    /*글 수정*/
    public void updatePost(CreateUpdatePostDto dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }
}


