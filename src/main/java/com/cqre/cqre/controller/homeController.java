package com.cqre.cqre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {

    @GetMapping({"/", "/home"})
    public String home(){
        return "/home/home";
    }

    @GetMapping("/signIn")
    public String signIn(){
        return "/user/signIn";
    }

    @GetMapping("/userInfo")
    public String userInfo(){
        return "/user/userInfo";
    }

    @GetMapping("/orderList")
    public String orderList(){
        return "/order/orderList";
    }

    @GetMapping("/basketList")
    public String basketList(){
        return "/basket/basketList";
    }

    @GetMapping("/orderCancelList")
    public String orderCancelList(){
        return "/order/orderCancelList";
    }

    @GetMapping("/couponList")
    public String couponList(){
        return "/coupon/couponList";
    }

    @GetMapping("/postList")
    public String postList(){
        return "/post/postList";
    }

    @GetMapping("/commentList")
    public String commentList(){
        return "/comment/commentList";
    }
}
