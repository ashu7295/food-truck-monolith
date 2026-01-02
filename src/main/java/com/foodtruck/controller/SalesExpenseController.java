package com.foodtruck.controller;

import com.foodtruck.dto.SalesExpenseRequestDto;
import com.foodtruck.dto.SalesExpenseResponseDto;
import com.foodtruck.enums.TransactionType;
import com.foodtruck.service.SalesExpenseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Finance", description = "Sales & expense tracking APIs")
@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class SalesExpenseController {

    private final SalesExpenseService salesExpenseService;

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<SalesExpenseResponseDto> create(
            @Valid @RequestBody SalesExpenseRequestDto dto) {

        SalesExpenseResponseDto response = salesExpenseService.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<SalesExpenseResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody SalesExpenseRequestDto dto) {

        SalesExpenseResponseDto response = salesExpenseService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<SalesExpenseResponseDto> getById(
            @PathVariable Long id) {

        SalesExpenseResponseDto response = salesExpenseService.getById(id);
        return ResponseEntity.ok(response);
    }

    // ================= GET ALL =================
    @GetMapping
    public ResponseEntity<List<SalesExpenseResponseDto>> getAll() {

        List<SalesExpenseResponseDto> records = salesExpenseService.getAll();
        return ResponseEntity.ok(records);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        salesExpenseService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    // ================= GET BY TYPE =================
    @GetMapping("/type")
    public ResponseEntity<List<SalesExpenseResponseDto>> getByType(
            @RequestParam TransactionType type) {

        return ResponseEntity.ok(salesExpenseService.getByType(type));
    }
//
//
//    // ================= GET TOTAL BY TYPE =================
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalByType(
            @RequestParam TransactionType type) {

        return ResponseEntity.ok(salesExpenseService.getTotalByType(type));
    }

}
