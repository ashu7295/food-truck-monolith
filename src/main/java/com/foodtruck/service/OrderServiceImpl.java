package com.foodtruck.service;

import com.foodtruck.dto.OrderItemRequestDto;
import com.foodtruck.dto.OrderRequestDto;
import com.foodtruck.dto.OrderItemResponseDto;
import com.foodtruck.dto.OrderResponseDto;
import com.foodtruck.entity.MenuItem;
import com.foodtruck.entity.Order;
import com.foodtruck.entity.OrderItem;
import com.foodtruck.enums.OrderStatus;
import com.foodtruck.exception.BadRequestException;
import com.foodtruck.exception.ResourceNotFoundException;
import com.foodtruck.repository.MenuItemRepository;
import com.foodtruck.repository.OrderRepository;
import com.foodtruck.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;

    // ================= PLACE ORDER =================
    @Override
    public OrderResponseDto placeOrder(OrderRequestDto dto) {

        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new BadRequestException("Order must contain at least one item");
        }

        Order order = Order.builder()
                .customerName(dto.getCustomerName())
                .status(OrderStatus.CREATED)
                .orderTime(LocalDateTime.now())
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequestDto itemDto : dto.getItems()) {

            MenuItem menuItem = menuItemRepository.findById(itemDto.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

            if (!menuItem.isAvailable()) {
                throw new BadRequestException(
                        "Menu item not available: " + menuItem.getName());
            }

            BigDecimal itemTotal =
                    menuItem.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .menuItem(menuItem)
                    .quantity(itemDto.getQuantity())
                    .price(itemTotal)
                    .build();

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    // ================= GET ORDER BY ID =================
    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return mapToResponse(order);
    }

    // ================= GET ALL ORDERS =================
    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAll() {

        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ================= MARK ORDER AS PAID =================
    @Override
    public OrderResponseDto markAsPaid(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new BadRequestException(
                    "Only CREATED orders can be marked as PAID");
        }

        order.setStatus(OrderStatus.PAID);
        return mapToResponse(orderRepository.save(order));
    }

    // ================= CANCEL ORDER =================
    @Override
    public OrderResponseDto cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.PAID) {
            throw new BadRequestException(
                    "Paid orders cannot be cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        return mapToResponse(orderRepository.save(order));
    }

    // ================= MAPPER =================
    private OrderResponseDto mapToResponse(Order order) {

        List<OrderItemResponseDto> itemResponses =
                order.getItems().stream()
                        .map(item -> OrderItemResponseDto.builder()
                                .itemName(item.getMenuItem().getName())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build())
                        .toList();

        return OrderResponseDto.builder()
                .orderId(order.getId())
                .customerName(order.getCustomerName())
                .status(order.getStatus())
                .orderTime(order.getOrderTime())
                .totalAmount(order.getTotalAmount())
                .items(itemResponses)
                .build();
    }
}
