package com.cqre.cqre.dto.coupon;

import lombok.Data;

@Data
public class SendCouponDto {

    private String name;
    private String email;
    private int count;
}
