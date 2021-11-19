package com.cqre.cqre.domain.board;

import com.cqre.cqre.dto.post.CreateAndUpdatePostDto;
import com.cqre.cqre.domain.BaseEntity;
import com.cqre.cqre.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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

    @Builder
    public Post(String title, String content, int postViews, int recommendation, User user, Board board, List<PostFile> postFiles, List<Recommendation> recommendations) {
        this.title = title;
        this.content = content;
        this.postViews = postViews;
        this.recommendation = recommendation;
        this.user = user;
        this.board = board;
    }

    public static Post of(String title, String content, User user, Board board){
        return Post.builder()
                .title(title)
                .content(content)
                .postViews(0)
                .recommendation(0)
                .user(user)
                .board(board)
                .build();
    }

    public void addPostViews(){
        this.postViews++;
    }

    public void addRecommendation(){
        this.recommendation++;
    }

    public void subtractRecommendation(){
        this.recommendation--;
    }

    public void updatePost(CreateAndUpdatePostDto dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

}


