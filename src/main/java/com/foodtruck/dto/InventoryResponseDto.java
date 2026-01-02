package com.foodtruck.dto;

import com.foodtruck.enums.InventoryUnit;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponseDto {

    private Long id;
    private String itemName;
    private int quantity;
    private InventoryUnit unit;
}
