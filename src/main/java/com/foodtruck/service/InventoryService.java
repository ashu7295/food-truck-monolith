package com.foodtruck.service;

import com.foodtruck.dto.InventoryRequestDto;
import com.foodtruck.dto.InventoryResponseDto;

import java.util.List;

public interface InventoryService {

    InventoryResponseDto create(InventoryRequestDto dto);

    InventoryResponseDto update(Long id, InventoryRequestDto dto);

    InventoryResponseDto getById(Long id);

    List<InventoryResponseDto> getAll();

    void delete(Long id);

    InventoryResponseDto addStock(Long id, int quantity);

    InventoryResponseDto reduceStock(Long id, int quantity);
}

