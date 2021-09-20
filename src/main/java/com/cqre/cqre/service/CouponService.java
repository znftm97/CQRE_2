package com.cqre.cqre.service;

import com.cqre.cqre.dto.coupon.CouponDto;
import com.cqre.cqre.dto.coupon.FindCouponDto;
import com.cqre.cqre.dto.coupon.SendCouponDto;
import com.cqre.cqre.domain.User;
import com.cqre.cqre.domain.shop.Coupon;
import com.cqre.cqre.domain.shop.UserCoupon;
import com.cqre.cqre.exception.customexception.CDiscountRateExceededException;
import com.cqre.cqre.exception.customexception.CEmptyValueException;
import com.cqre.cqre.repository.CouponRepository;
import com.cqre.cqre.repository.UserCouponRepository;
import com.cqre.cqre.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final UserCouponRepository userCouponRepository;
    private final UserService userService;

    /*쿠폰생성*/
    @Transactional
    public void createCoupon(CouponDto dto) {
        User admin = userRepository.findByEmail("admin");

        if (dto.getDiscountRate() >= 100) {
            throw new CDiscountRateExceededException();
        } else if (dto.getName().equals("")) {
            throw new CEmptyValueException();
        }

        Coupon coupon = Coupon.builder()
                .name(dto.getName())
                .discountRate(dto.getDiscountRate())
                .totalCount(dto.getTotalCount())
                .remainCount(dto.getTotalCount())
                .user(admin)
                .build();

        couponRepository.save(coupon);
    }

    /*쿠폰목록 조회*/
    public Page<CouponDto> findCoupons(Pageable pageable) {
        Page<Coupon> findCoupons = couponRepository.findAllCoupon(pageable);
        return findCoupons.map(c -> new CouponDto(c));
    }

    /*쿠폰 전송*/
    @Transactional
    public void sendCoupon(SendCouponDto dto) {
        User findUser = userRepository.findByEmail(dto.getEmail());
        Coupon findCoupon = couponRepository.findByName(dto.getName());

        UserCoupon userCoupon = UserCoupon.builder()
                .count(dto.getCount())
                .user(findUser)
                .coupon(findCoupon)
                .build();

        findCoupon.removeCount(dto.getCount());

        userCouponRepository.save(userCoupon);
    }

    /*내 쿠폰 목록 조회*/
    public Page<FindCouponDto> myCoupons(Pageable pageable) {
        User loginUser = userService.getLoginUser();
        List<UserCoupon> result = userCouponRepository.findUserCouponsByUserIdWithCoupon(loginUser.getId());
        List<Long> couponIds = result.stream()
                .map(uc -> uc.getCoupon().getId())
                .collect(Collectors.toList());

        return couponRepository.findCouponsById(couponIds, pageable).map(c -> new FindCouponDto(c));
    }

    /*내 쿠폰 목록 조회 페이징x*/
    public List<FindCouponDto> myCouponsNotPaging() {
        User loginUser = userService.getLoginUser();
        List<UserCoupon> result = userCouponRepository.findUserCouponsByUserIdWithCoupon(loginUser.getId());
        return result.stream()
                .map(us -> new FindCouponDto(us))
                .collect(Collectors.toList());
    }

}

