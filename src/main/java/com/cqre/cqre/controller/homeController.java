package com.cqre.cqre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {

    @GetMapping({"/", "/home"})
    public String home(){
        return "/home/home";
    }

    @GetMapping("/basketList")
    public String basketList(){
        return "/basket/basketList";
    }

    @GetMapping("/orderCancelList")
    public String orderCancelList(){
        return "/order/orderCancelList";
    }

    @GetMapping("/myCouponList")
    public String myCouponList(){
        return "/coupon/myCouponList";
    }

    @GetMapping("/history")
    public String history(){
        return "/history/history";
    }

    @GetMapping("/couponList")
    public String couponList(){
        return "/coupon/couponList";
    }

    @GetMapping("/createCoupon")
    public String createCoupon(){
        return "/coupon/createCoupon";
    }

}
