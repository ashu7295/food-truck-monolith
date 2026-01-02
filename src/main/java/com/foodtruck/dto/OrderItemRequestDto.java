package com.foodtruck.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDto {

    @NotNull
    private Long menuItemId;

    @Min(1)
    private int quantity;
}