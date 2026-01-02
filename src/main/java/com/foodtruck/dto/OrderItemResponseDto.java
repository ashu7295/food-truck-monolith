package com.foodtruck.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDto {

    private String itemName;
    private int quantity;
    private BigDecimal price;
}
