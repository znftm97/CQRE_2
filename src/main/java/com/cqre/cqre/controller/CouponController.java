package com.cqre.cqre.controller;

import com.cqre.cqre.dto.coupon.CouponDto;
import com.cqre.cqre.dto.coupon.FindCouponDto;
import com.cqre.cqre.dto.coupon.SendCouponDto;
import com.cqre.cqre.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CouponController {

    private final CouponService couponService;

    /*쿠폰 생성 페이지*/
    @GetMapping("/createCoupon")
    public String createCoupon(){
        return "/coupon/createCoupon";
    }

    /*쿠폰 생성*/
    @PostMapping("/createCoupon")
    public String PCreateCoupon(@ModelAttribute CouponDto dto) {
        couponService.createCoupon(dto);

        return "redirect:/couponList";
    }

    /*쿠폰목록 조회*/
    @GetMapping("/couponList")
    public String coupons(@PageableDefault(size = 7, sort = "id")Pageable pageable, Model model){
        Page<CouponDto> coupons = couponService.findCoupons(pageable);
        model.addAttribute("coupons", coupons);
        return "/coupon/couponList";
    }

    /*쿠폰 전송*/
    @PostMapping("/coupon/send")
    public String sendCoupon(@ModelAttribute SendCouponDto dto) {
        couponService.sendCoupon(dto);
        return "redirect:/couponList";
    }

    /*내 쿠폰 목록*/
    @GetMapping("/myCouponList")
    public String myCoupons(@PageableDefault(size = 5, sort = "id")Pageable pageable, Model model){
        Page<FindCouponDto> coupons = couponService.myCoupons(pageable);
        model.addAttribute("coupons", coupons);

        return "/coupon/myCouponList";
    }
}
