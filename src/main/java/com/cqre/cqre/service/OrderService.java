package com.cqre.cqre.service;

import com.cqre.cqre.dto.order.FindOrderItemDto;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.entity.shop.Order;
import com.cqre.cqre.entity.shop.OrderItem;
import com.cqre.cqre.entity.shop.OrderStatus;
import com.cqre.cqre.entity.shop.item.Item;
import com.cqre.cqre.exception.customexception.CNotEnoughStockException;
import com.cqre.cqre.repository.Item.ItemRepository;
import com.cqre.cqre.repository.order.OrderItemRepository;
import com.cqre.cqre.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;

    /*주문 생성*/
    @Transactional
    public void createOrder(Long itemId, int count) {
        Item findItem = itemRepository.findItemById(itemId);
        User findUser = userService.getLoginUser();

        OrderItem orderItem = OrderItem.builder()
                .item(findItem)
                .orderPrice(findItem.getPrice()*count)
                .count(count)
                .build();

        findItem.removeStock(orderItem.getCount());

        Order order = Order.createOrder(findUser, orderItem);

        orderRepository.save(order);
    }

    /*주문 목록 조회*/
    public Page<FindOrderItemDto> findOrders(Pageable pageable) {
        User loginUser = userService.getLoginUser();
        Page<OrderItem> findOrderItems = orderItemRepository.findOrderItemByUserId(loginUser.getId(), OrderStatus.ORDER ,pageable);
        return findOrderItems.map(o -> new FindOrderItemDto(o));
    }

    /*주문 취소*/
    @Transactional
    public void orderCancel(Long orderItemId) {
        OrderItem findOrderItem = orderItemRepository.findOrderItemWithOrder(orderItemId);
        findOrderItem.getOrder().cancelOrder();
    }

    /*주문 취소 목록 조회*/
    public Page<FindOrderItemDto> findCancelOrders(Pageable pageable) {
        User loginUser = userService.getLoginUser();
        Page<OrderItem> findOrderItems = orderItemRepository.findOrderByOrderStatus(loginUser.getId(), OrderStatus.CANCEL, pageable);
        return findOrderItems.map(o -> new FindOrderItemDto(o));
    }

    /*재 주문*/
    @Transactional
    public void reOrder(Long orderItemId) {
        OrderItem findOrderItem = orderItemRepository.findOrderItemWithOrder(orderItemId);
        findOrderItem.getOrder().reOrder();
    }

    /*장바구니 추가*/
    @Transactional
    public void createBasket(Long itemId, int count) {
        User loginUser = userService.getLoginUser();
        Item findItem = itemRepository.findItemById(itemId);

        OrderItem orderItem = OrderItem.builder()
                .item(findItem)
                .orderPrice(findItem.getPrice()*count)
                .count(count)
                .build();

        if (findItem.getStockCount() < count) {
            throw new CNotEnoughStockException();
        }

        Order order = Order.createBasket(loginUser, orderItem);
        orderRepository.save(order);
    }

    /*장바구니 목록 조회*/
    public Page<FindOrderItemDto> findBaskets(Pageable pageable) {
        User loginUser = userService.getLoginUser();
        Page<OrderItem> findOrderItems = orderItemRepository.findOrderByOrderStatus(loginUser.getId(), OrderStatus.BASKET, pageable);
        return findOrderItems.map(o -> new FindOrderItemDto(o));
    }

    /*장바구니에서 바로 주문*/
    @Transactional
    public void basketOrder(Long orderItemId) {
        OrderItem findOrderItem = orderItemRepository.findOrderItemWithOrder(orderItemId);
        findOrderItem.getOrder().reOrder();
    }

    /*장바구니 삭제*/
    @Transactional
    public void basketCancel(Long orderItemId) {
        OrderItem findOrderItem = orderItemRepository.findOrderItemWithOrder(orderItemId);
        orderItemRepository.delete(findOrderItem);
        orderRepository.delete(findOrderItem.getOrder());
    }

}
