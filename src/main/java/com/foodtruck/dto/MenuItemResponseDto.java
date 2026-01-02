package com.foodtruck.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemResponseDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean available;
}

