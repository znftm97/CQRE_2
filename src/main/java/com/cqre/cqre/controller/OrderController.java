package com.cqre.cqre.controller;

import com.cqre.cqre.dto.order.FindOrderItemDto;
import com.cqre.cqre.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /*주문*/
    @PostMapping("/order")
    public String createOrder(@RequestParam("itemId") Long itemId,
                              @RequestParam("count") int count,
                              @RequestParam("userCouponId") String userCouponId) {
        if (userCouponId.equals("null")) {
            orderService.createOrder(itemId, count);
        } else {
            orderService.createOrderWithCoupon(itemId, count, userCouponId);
        }

        return "redirect:/orderList";
    }

    /*주문목록*/
    @GetMapping("/orderList")
    public String orderList(@PageableDefault(size = 5, sort = "id") Pageable pageable, Model model) {
        Page<FindOrderItemDto> orderItems = orderService.findOrders(pageable);
        model.addAttribute("orderItems", orderItems);
        return "/order/orderList";
    }

    /*주문 취소*/
    @PostMapping("/orderCancel/{orderItemId}")
    public String orderCancel(@PathVariable("orderItemId") Long orderItemId) {
        orderService.orderCancel(orderItemId);

        return "redirect:/orderCancelList";
    }

    /*주문 취소 목록*/
    @GetMapping("/orderCancelList")
    public String orderCancelList(@PageableDefault(size = 5, sort = "id") Pageable pageable, Model model) {
        Page<FindOrderItemDto> orderItems = orderService.findCancelOrders(pageable);
        model.addAttribute("orderItems", orderItems);
        return "/order/orderCancelList";
    }

    /*재 주문*/
    @PostMapping("/reOrder/{orderItemId}")
    public String reOrder(@PathVariable("orderItemId") Long orderItemId) {
        orderService.reOrder(orderItemId);

        return "redirect:/orderList";
    }

    /*장바구니 추가*/
    @PostMapping("/basket")
    public String createBasket(@RequestParam("itemId") Long itemId,
                               @RequestParam("count") int count) {
        orderService.createBasket(itemId, count);

        return "redirect:/basketList";
    }

    /*장바구니 목록 조회*/
    @GetMapping("/basketList")
    public String findBaskets(@PageableDefault(size = 5, sort = "id")Pageable pageable, Model model) {
        Page<FindOrderItemDto> orderItems = orderService.findBaskets(pageable);
        model.addAttribute("orderItems", orderItems);

        return "/basket/basketList";
    }

    /*장바구니에서 바로 주문*/
    @PostMapping("/basketOrder/{orderItemId}")
    public String basketOrder(@PathVariable("orderItemId") Long orderItemId) {
        orderService.basketOrder(orderItemId);

        return "redirect:/orderList";
    }

    /*장바구니 삭제*/
    @PostMapping("/basketCancel/{orderItemId}")
    public String basketCancel(@PathVariable("orderItemId") Long orderItemId) {
        orderService.basketCancel(orderItemId);

        return "redirect:/basketList";
    }
}
