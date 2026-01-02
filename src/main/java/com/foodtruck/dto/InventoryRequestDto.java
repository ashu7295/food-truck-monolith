package com.foodtruck.dto;

import com.foodtruck.enums.InventoryUnit;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequestDto {

    @NotBlank
    private String itemName;

    @Min(0)
    private int quantity;

    @NotNull
    private InventoryUnit unit;
}
