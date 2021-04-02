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
    public String orderList(@PageableDefault(size = 5, sort = "id") Pageable pageable, Model model){
        Page<FindOrderItemDto> orders = orderService.findOrders(pageable);
        model.addAttribute("orders", orders);
        return "/order/orderList";
    }
}
