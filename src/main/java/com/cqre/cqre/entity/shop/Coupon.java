package com.cqre.cqre.entity.shop;

import com.cqre.cqre.entity.BaseEntity;
import com.cqre.cqre.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    private String name;

    private Long discountRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
