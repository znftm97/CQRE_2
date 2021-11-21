package com.cqre.cqre.service;

import com.cqre.cqre.domain.shop.Coupon;
import com.cqre.cqre.domain.shop.UserCoupon;
import com.cqre.cqre.domain.user.User;
import com.cqre.cqre.dto.coupon.CouponDto;
import com.cqre.cqre.dto.coupon.FindCouponDto;
import com.cqre.cqre.dto.coupon.SendCouponDto;
import com.cqre.cqre.exception.customexception.user.CUserNotFoundException;
import com.cqre.cqre.exception.customexception.user.CUserNotFoundExceptionToCouponPage;
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
    public void createCoupon(CouponDto CouponDto) {
        User admin = userRepository.findByEmail("admin").orElseThrow(CUserNotFoundException::new);
        couponRepository.save(CouponDto.toEntity(admin));
    }

    /*쿠폰목록 조회*/
    public Page<CouponDto> findCoupons(Pageable pageable) {
        Page<Coupon> findCoupons = couponRepository.findAllCoupon(pageable);
        return findCoupons.map(c -> new CouponDto(c));
    }

    /*쿠폰 전송*/
    @Transactional
    public void sendCoupon(SendCouponDto dto) {
        User findUser = userRepository.findByEmail(dto.getEmail()).orElseThrow(CUserNotFoundExceptionToCouponPage::new);
        Coupon findCoupon = couponRepository.findByName(dto.getName());
        int couponCount = dto.getCount();

        findCoupon.removeCount(couponCount);
        userCouponRepository.save(UserCoupon.of(findCoupon, findUser, couponCount));
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

