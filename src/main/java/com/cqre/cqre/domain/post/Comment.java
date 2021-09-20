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

    /*댓글 수정*/
    public void updateComment(String content){
        this.content = content;
    }

    /*댓글 삭제*/
    public void removeComment(){
        this.existsCheck = false;
    }
}