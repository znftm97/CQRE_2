package com.cqre.cqre.dto.coupon;

import com.cqre.cqre.entity.shop.Coupon;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CouponDto {

    private Long id;
    private String name;
    private Long  discountRate;
    private LocalDateTime createDate;
    private int totalCount;
    private int remainCount;

    public CouponDto(Coupon coupon) {
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.discountRate = coupon.getDiscountRate();
        this.createDate = coupon.getCreateDate();
        this.totalCount = coupon.getTotalCount();
        this.remainCount = coupon.getRemainCount();
    }

    public CouponDto() {
    }
}
