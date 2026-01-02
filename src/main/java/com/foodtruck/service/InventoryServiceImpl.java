package com.foodtruck.service;

import com.foodtruck.dto.InventoryRequestDto;
import com.foodtruck.dto.InventoryResponseDto;
import com.foodtruck.entity.Inventory;
import com.foodtruck.exception.BadRequestException;
import com.foodtruck.exception.ResourceNotFoundException;
import com.foodtruck.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    // ================= CREATE =================
    @Override
    public InventoryResponseDto create(InventoryRequestDto dto) {

        Inventory inventory = Inventory.builder()
                .itemName(dto.getItemName())
                .quantity(dto.getQuantity())
                .unit(dto.getUnit())
                .build();

        Inventory saved = inventoryRepository.save(inventory);
        return mapToResponse(saved);
    }

    // ================= UPDATE =================
    @Override
    public InventoryResponseDto update(Long id, InventoryRequestDto dto) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found"));

        inventory.setItemName(dto.getItemName());
        inventory.setQuantity(dto.getQuantity());
        inventory.setUnit(dto.getUnit());

        return mapToResponse(inventoryRepository.save(inventory));
    }

    // ================= GET BY ID =================
    @Override
    @Transactional(readOnly = true)
    public InventoryResponseDto getById(Long id) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found"));

        return mapToResponse(inventory);
    }

    // ================= GET ALL =================
    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponseDto> getAll() {

        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ================= DELETE =================
    @Override
    public void delete(Long id) {

        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory item not found");
        }

        inventoryRepository.deleteById(id);
    }

    // ================= ADD STOCK =================
    @Override
    public InventoryResponseDto addStock(Long id, int quantity) {

        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than zero");
        }

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found"));

        inventory.setQuantity(inventory.getQuantity() + quantity);

        return mapToResponse(inventoryRepository.save(inventory));
    }

    // ================= REDUCE STOCK =================
    @Override
    public InventoryResponseDto reduceStock(Long id, int quantity) {

        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than zero");
        }

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found"));

        if (inventory.getQuantity() < quantity) {
            throw new BadRequestException("Insufficient stock available");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);

        return mapToResponse(inventoryRepository.save(inventory));
    }

    // ================= MAPPER =================
    private InventoryResponseDto mapToResponse(Inventory inventory) {
        return InventoryResponseDto.builder()
                .id(inventory.getId())
                .itemName(inventory.getItemName())
                .quantity(inventory.getQuantity())
                .unit(inventory.getUnit())
                .build();
    }
}

