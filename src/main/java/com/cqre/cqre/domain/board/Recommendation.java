package com.cqre.cqre.domain.board;

import com.cqre.cqre.domain.BaseEntity;
import com.cqre.cqre.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
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

    @Builder
    public Recommendation(User user){
        this.user = user;
    }

    public static Recommendation of(User user, Post post){
        Recommendation recommendation = Recommendation.builder().user(user).build();
        recommendation.setPost(post);

        return recommendation;
    }

    public void setPost(Post post) {
        this.post = post;
        post.getRecommendations().add(this);
    }

}
