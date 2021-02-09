package com.cqre.cqre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {

    @GetMapping({"/", "/home"})
    public String home(){
        return "/home/home";
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

    @GetMapping("/myCouponList")
    public String myCouponList(){
        return "/coupon/myCouponList";
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

    @GetMapping("/createNoticePost")
    public String createNoticePost(){
        return "/post/createNoticePost";
    }

    @GetMapping("/createFreePost")
    public String createFreePost(){
        return "/post/createFreePost";
    }

    @GetMapping("/history")
    public String history(){
        return "/history/history";
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

    @GetMapping("/createGallery")
    public String createGallery(){
        return "/gallery/createGallery";
    }

    @GetMapping("/createOrder")
    public String createOrder(){
        return "/order/createOrder";
    }

    @GetMapping("/couponList")
    public String couponList(){
        return "/coupon/couponList";
    }

    @GetMapping("/createCoupon")
    public String createCoupon(){
        return "/coupon/createCoupon";
    }

    @GetMapping("/selectItemType")
    public String selectItemType(){
        return "/item/selectItemType";
    }

    @GetMapping("/createBook")
    public String createBook(){
        return "/item/createBook";
    }

    @GetMapping("/createDailyNecessity")
    public String createDailyNecessity(){
        return "/item/createDailyNecessity";
    }

    @GetMapping("/createPcParts")
    public String createPcParts(){
        return "/item/createPcParts";
    }

    @GetMapping("/readPost")
    public String readPost(){
        return "/post/readPost";
    }


}
