package com.foodtruck.service;

import com.foodtruck.dto.OrderRequestDto;
import com.foodtruck.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto placeOrder(OrderRequestDto dto);

    OrderResponseDto getById(Long id);

    List<OrderResponseDto> getAll();

    OrderResponseDto markAsPaid(Long orderId);

    OrderResponseDto cancelOrder(Long orderId);
}

