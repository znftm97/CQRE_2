package com.cqre.cqre.dto.coupon;

import com.cqre.cqre.domain.shop.Coupon;
import com.cqre.cqre.domain.shop.UserCoupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindCouponDto {

    private Long userCouponId;
    private String name;
    private Long discountRate;

    public FindCouponDto(Coupon coupon) {
        this.name = coupon.getName();
        this.discountRate = coupon.getDiscountRate();
    }

    public FindCouponDto(UserCoupon userCoupon) {
        this.userCouponId = userCoupon.getId();
        this.name = userCoupon.getCoupon().getName();
        this.discountRate = userCoupon.getCoupon().getDiscountRate();
    }
}
