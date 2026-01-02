package com.foodtruck.service;

import com.foodtruck.dto.SalesExpenseRequestDto;
import com.foodtruck.dto.SalesExpenseResponseDto;
import com.foodtruck.enums.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public interface SalesExpenseService {

    SalesExpenseResponseDto create(SalesExpenseRequestDto dto);

    SalesExpenseResponseDto update(Long id, SalesExpenseRequestDto dto);

    SalesExpenseResponseDto getById(Long id);

    List<SalesExpenseResponseDto> getAll();

    void delete(Long id);

    List<SalesExpenseResponseDto> getByType(TransactionType type);

    BigDecimal getTotalByType(TransactionType type);
}
