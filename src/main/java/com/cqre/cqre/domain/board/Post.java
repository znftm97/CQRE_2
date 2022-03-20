package com.cqre.cqre.domain.board;

import com.cqre.cqre.domain.BaseEntity;
import com.cqre.cqre.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Builder
    public Post(String title, String content, int postViews, int recommendation, User user, Board board) {
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

    public void updatePost(String title, String content){
        this.title = getTitle();
        this.content = getContent();
    }

}


