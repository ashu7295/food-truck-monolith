package com.foodtruck.controller;

import com.foodtruck.dto.MenuItemRequestDto;
import com.foodtruck.dto.MenuItemResponseDto;
import com.foodtruck.service.MenuItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Menu", description = "Menu item management APIs")
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuItemService menuItemService;

    // ================= CREATE MENU ITEM =================
    @PostMapping
    public ResponseEntity<MenuItemResponseDto> createMenuItem(
            @Valid @RequestBody MenuItemRequestDto dto) {

        MenuItemResponseDto response = menuItemService.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ================= UPDATE MENU ITEM =================
    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponseDto> updateMenuItem(
            @PathVariable Long id,
            @Valid @RequestBody MenuItemRequestDto dto) {

        MenuItemResponseDto response = menuItemService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    // ================= GET MENU ITEM BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDto> getMenuItemById(
            @PathVariable Long id) {

        MenuItemResponseDto response = menuItemService.getById(id);
        return ResponseEntity.ok(response);
    }

    // ================= GET ALL MENU ITEMS =================
    @GetMapping
    public ResponseEntity<List<MenuItemResponseDto>> getAllMenuItems() {

        List<MenuItemResponseDto> items = menuItemService.getAll();
        return ResponseEntity.ok(items);
    }

    // ================= GET AVAILABLE ITEMS =================
    @GetMapping("/available")
    public ResponseEntity<List<MenuItemResponseDto>> getAvailableMenuItems() {

        List<MenuItemResponseDto> items = menuItemService.getAvailableItems();
        return ResponseEntity.ok(items);
    }

    // ================= DELETE MENU ITEM =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {

        menuItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
