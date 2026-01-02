package com.foodtruck.service;

import com.foodtruck.dto.SalesExpenseRequestDto;
import com.foodtruck.dto.SalesExpenseResponseDto;
import com.foodtruck.entity.SalesExpense;
import com.foodtruck.enums.TransactionType;
import com.foodtruck.exception.ResourceNotFoundException;
import com.foodtruck.repository.SalesExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesExpenseServiceImpl implements SalesExpenseService {

    private final SalesExpenseRepository salesExpenseRepository;

    // ================= CREATE =================
    @Override
    public SalesExpenseResponseDto create(SalesExpenseRequestDto dto) {

        SalesExpense salesExpense = SalesExpense.builder()
                .type(dto.getType())
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .date(dto.getDate())
                .build();

        SalesExpense saved = salesExpenseRepository.save(salesExpense);
        return mapToResponse(saved);
    }

    // ================= UPDATE =================
    @Override
    public SalesExpenseResponseDto update(Long id, SalesExpenseRequestDto dto) {

        SalesExpense existing = salesExpenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales/Expense record not found"));

        existing.setType(dto.getType());
        existing.setAmount(dto.getAmount());
        existing.setDescription(dto.getDescription());
        existing.setDate(dto.getDate());

        return mapToResponse(salesExpenseRepository.save(existing));
    }

    // ================= GET BY ID =================
    @Override
    @Transactional(readOnly = true)
    public SalesExpenseResponseDto getById(Long id) {

        SalesExpense record = salesExpenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales/Expense record not found"));

        return mapToResponse(record);
    }

    // ================= GET ALL =================
    @Override
    @Transactional(readOnly = true)
    public List<SalesExpenseResponseDto> getAll() {

        return salesExpenseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ================= DELETE =================
    @Override
    public void delete(Long id) {

        if (!salesExpenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sales/Expense record not found");
        }

        salesExpenseRepository.deleteById(id);
    }

    // ================= FILTER BY TYPE =================
    @Override
    @Transactional(readOnly = true)
    public List<SalesExpenseResponseDto> getByType(TransactionType type) {

        return salesExpenseRepository.findAll()
                .stream()
                .filter(record -> record.getType() == type)
                .map(this::mapToResponse)
                .toList();
    }

    // ================= TOTAL BY TYPE =================
    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalByType(TransactionType type) {

        return salesExpenseRepository.findAll()
                .stream()
                .filter(record -> record.getType() == type)
                .map(SalesExpense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // ================= MAPPER =================
    private SalesExpenseResponseDto mapToResponse(SalesExpense record) {

        return SalesExpenseResponseDto.builder()
                .id(record.getId())
                .type(record.getType())
                .amount(record.getAmount())
                .description(record.getDescription())
                .date(record.getDate())
                .build();
    }
}

