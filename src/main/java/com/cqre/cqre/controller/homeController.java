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

    @GetMapping("/noticeBoard")
    public String noticeBoard(){
        return "/board/noticeBoard";
    }

    @GetMapping("/freeBoard")
    public String freeBoard(){
        return "/board/freeBoard";
    }

    @GetMapping("/noticeCreate")
    public String noticeCreate(){
        return "/board/noticeCreate";
    }

    @GetMapping("/freeCreate")
    public String freeCreate(){
        return "/board/freeCreate";
    }

    @GetMapping("/history")
    public String history(){
        return "/history/history";
    }

    @GetMapping("/idpwSearch")
    public String idpwSearch(){
        return "/user/idpwSearch";
    }

    @GetMapping("/updatePassword")
    public String updatePassword(){
        return "/user/updatePassword";
    }

    @GetMapping("/gallery")
    public String gallery(){
        return "/gallery/gallery";
    }

    @GetMapping("/shop")
    public String shop(){
        return "/shop/shop";
    }

}
