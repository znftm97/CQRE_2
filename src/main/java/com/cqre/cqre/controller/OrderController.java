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
                              @RequestParam("count") int count) {
        orderService.createOrder(itemId, count);

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
    public String orderCancelList(@PageableDefault(size = 5, sort = "id")Pageable pageable, Model model) {
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
}
