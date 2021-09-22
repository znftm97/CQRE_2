package com.cqre.cqre.domain.post;

import com.cqre.cqre.dto.post.CreateAndUpdatePostDto;
import com.cqre.cqre.domain.BaseEntity;
import com.cqre.cqre.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "post")
    private List<PostFile> postFiles = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Recommendation> recommendations = new ArrayList<>();

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
    public void updatePost(CreateAndUpdatePostDto dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    //==생성 메서드==//
    public static Post createPost(String title, String content, int postViews, int recommendation, User user, Board board){
        Post post = new Post();
        post.title = title;
        post.content = content;
        post.postViews = postViews;
        post.recommendation = recommendation;
        post.user = user;
        post.board = board;

        return post;
    }
}


