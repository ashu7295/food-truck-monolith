package com.foodtruck.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

    private String customerName;

    @NotEmpty
    private List<OrderItemRequestDto> items;
}