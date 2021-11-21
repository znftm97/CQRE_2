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
public class UserCoupon extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "usercoupon_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int count;

    @Builder
    public UserCoupon(Coupon coupon, User user, int count) {
        this.coupon = coupon;
        this.user = user;
        this.count = count;
    }

    public static UserCoupon of(Coupon coupon, User user, int count){
        return UserCoupon.builder()
                    .coupon(coupon)
                    .user(user)
                    .count(count)
                    .build();
    }
}
