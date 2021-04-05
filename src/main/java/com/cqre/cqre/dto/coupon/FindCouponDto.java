package com.cqre.cqre.dto.coupon;

import com.cqre.cqre.entity.shop.Coupon;
import com.cqre.cqre.entity.shop.UserCoupon;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
