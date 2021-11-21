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
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private int depth;

    private Long bundleId;

    private Long bundleOrder;

    private boolean existsCheck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Comment(String content, int depth, Long bundleId, Long bundleOrder, boolean existsCheck, User user, Post post) {
        this.content = content;
        this.depth = depth;
        this.bundleId = bundleId;
        this.bundleOrder = bundleOrder;
        this.existsCheck = existsCheck;
        this.user = user;
        this.post = post;
    }

    public void updateComment(String content){
        this.content = content;
    }

    public void removeComment(){
        this.existsCheck = false;
    }
}
