package com.cqre.cqre.domain.shop;

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
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    private String name;

    private Long discountRate;

    private Integer totalCount;

    private int remainCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Coupon(String name, Long discountRate, Integer totalCount, int remainCount, User user) {
        this.name = name;
        this.discountRate = discountRate;
        this.totalCount = totalCount;
        this.remainCount = remainCount;
        this.user = user;
    }

    public void removeCount(int sendCount) {
        this.remainCount = totalCount - sendCount;
    }
}
