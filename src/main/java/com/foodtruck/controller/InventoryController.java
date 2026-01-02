package com.foodtruck.controller;

import com.foodtruck.dto.InventoryRequestDto;
import com.foodtruck.dto.InventoryResponseDto;
import com.foodtruck.service.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Inventory", description = "Inventory & stock management APIs")
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // ================= CREATE INVENTORY ITEM =================
    @PostMapping
    public ResponseEntity<InventoryResponseDto> createInventory(
            @Valid @RequestBody InventoryRequestDto dto) {

        InventoryResponseDto response = inventoryService.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ================= UPDATE INVENTORY ITEM =================
    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDto> updateInventory(
            @PathVariable Long id,
            @Valid @RequestBody InventoryRequestDto dto) {

        InventoryResponseDto response = inventoryService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    // ================= GET INVENTORY BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDto> getInventoryById(
            @PathVariable Long id) {

        InventoryResponseDto response = inventoryService.getById(id);
        return ResponseEntity.ok(response);
    }

    // ================= GET ALL INVENTORY =================
    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> getAllInventory() {

        List<InventoryResponseDto> inventory = inventoryService.getAll();
        return ResponseEntity.ok(inventory);
    }

    // ================= DELETE INVENTORY ITEM =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(
            @PathVariable Long id) {

        inventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ================= ADD STOCK =================
    @PutMapping("/{id}/add-stock")
    public ResponseEntity<InventoryResponseDto> addStock(
            @PathVariable Long id,
            @RequestParam int quantity) {

        InventoryResponseDto response = inventoryService.addStock(id, quantity);
        return ResponseEntity.ok(response);
    }

    // ================= REDUCE STOCK =================
    @PutMapping("/{id}/reduce-stock")
    public ResponseEntity<InventoryResponseDto> reduceStock(
            @PathVariable Long id,
            @RequestParam int quantity) {

        InventoryResponseDto response = inventoryService.reduceStock(id, quantity);
        return ResponseEntity.ok(response);
    }
}

