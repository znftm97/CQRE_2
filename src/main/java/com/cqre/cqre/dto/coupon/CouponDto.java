package com.cqre.cqre.dto.coupon;

import com.cqre.cqre.domain.shop.Coupon;
import com.cqre.cqre.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CouponDto {

    private Long id;

    @NotEmpty(message = "필수로 입력해야 합니다.")
    private String name;

    @NotNull(message = "필수로 입력해야 합니다.")
    @Max(message = "할인율은 100 미만이여야 합니다.", value = 99)
    @Min(message = "할인율은 0 초과여야 합니다.", value = 1)
    private Long discountRate;

    private LocalDateTime createDate;

    @NotNull(message = "필수로 입력해야 합니다.")
    @Max(message = "수량은 1000 미만이여야 합니다.", value = 999)
    @Min(message = "수량은 0 초과여야 합니다.", value = 1)
    private Integer totalCount;

    private int remainCount;

    public CouponDto(Coupon coupon) {
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.discountRate = coupon.getDiscountRate();
        this.createDate = coupon.getCreateDate();
        this.totalCount = coupon.getTotalCount();
        this.remainCount = coupon.getRemainCount();
    }

    public Coupon toEntity(User user){
        return Coupon.builder()
                    .name(name)
                    .discountRate(discountRate)
                    .totalCount(totalCount)
                    .remainCount(totalCount)
                    .user(user)
                    .build();
    }

}
