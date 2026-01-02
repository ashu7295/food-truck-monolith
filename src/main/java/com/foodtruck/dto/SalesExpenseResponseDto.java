package com.foodtruck.dto;

import com.foodtruck.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesExpenseResponseDto {

    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
}
