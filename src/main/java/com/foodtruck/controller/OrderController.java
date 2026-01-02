package com.foodtruck.controller;

import com.foodtruck.dto.OrderRequestDto;
import com.foodtruck.dto.OrderResponseDto;
import com.foodtruck.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Orders", description = "Order processing APIs")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // ================= PLACE ORDER =================
    @PostMapping
    public ResponseEntity<OrderResponseDto> placeOrder(
            @Valid @RequestBody OrderRequestDto dto) {

        OrderResponseDto response = orderService.placeOrder(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ================= GET ORDER BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @PathVariable Long id) {

        OrderResponseDto response = orderService.getById(id);
        return ResponseEntity.ok(response);
    }

    // ================= GET ALL ORDERS =================
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {

        List<OrderResponseDto> orders = orderService.getAll();
        return ResponseEntity.ok(orders);
    }

    // ================= MARK ORDER AS PAID =================
    @PutMapping("/{id}/pay")
    public ResponseEntity<OrderResponseDto> markOrderAsPaid(
            @PathVariable Long id) {

        OrderResponseDto response = orderService.markAsPaid(id);
        return ResponseEntity.ok(response);
    }

    // ================= CANCEL ORDER =================
    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrder(
            @PathVariable Long id) {

        OrderResponseDto response = orderService.cancelOrder(id);
        return ResponseEntity.ok(response);
    }
}

