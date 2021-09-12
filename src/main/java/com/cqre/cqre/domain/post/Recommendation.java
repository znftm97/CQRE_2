package com.cqre.cqre.domain.post;

import com.cqre.cqre.domain.BaseEntity;
import com.cqre.cqre.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommendation extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "recommendation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //== 연관관계 편의 메서드 ==//
    public void setPost(Post post) {
        this.post = post;
        post.getRecommendations().add(this);
    }

    @Builder
    public Recommendation(User user, Post post){
        this.user = user;
        setPost(post);
    }

    //==생성 메서드==//
    public static Recommendation createRecommendation(User user, Post post){
        Recommendation recommendation = new Recommendation();
        recommendation.user = user;
        recommendation.setPost(post);

        return recommendation;
    }
}
