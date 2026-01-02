package com.foodtruck.service;

import com.foodtruck.dto.MenuItemRequestDto;
import com.foodtruck.dto.MenuItemResponseDto;
import com.foodtruck.entity.MenuItem;
import com.foodtruck.exception.ResourceNotFoundException;
import com.foodtruck.repository.MenuItemRepository;
import com.foodtruck.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    // ================= CREATE =================
    @Override
    public MenuItemResponseDto create(MenuItemRequestDto dto) {

        MenuItem item = MenuItem.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .available(true)
                .build();

        MenuItem saved = menuItemRepository.save(item);
        return mapToResponse(saved);
    }

    // ================= UPDATE =================
    @Override
    public MenuItemResponseDto update(Long id, MenuItemRequestDto dto) {

        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());

        MenuItem updated = menuItemRepository.save(item);
        return mapToResponse(updated);
    }

    // ================= GET BY ID =================
    @Override
    @Transactional(readOnly = true)
    public MenuItemResponseDto getById(Long id) {

        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        return mapToResponse(item);
    }

    // ================= GET ALL =================
    @Override
    @Transactional(readOnly = true)
    public List<MenuItemResponseDto> getAll() {

        return menuItemRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ================= DELETE =================
    @Override
    public void delete(Long id) {

        if (!menuItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu item not found");
        }

        menuItemRepository.deleteById(id);
    }

    // ================= CUSTOM API =================
    @Override
    @Transactional(readOnly = true)
    public List<MenuItemResponseDto> getAvailableItems() {

        return menuItemRepository.findAll()
                .stream()
                .filter(MenuItem::isAvailable)
                .map(this::mapToResponse)
                .toList();
    }

    // ================= MAPPER =================
    private MenuItemResponseDto mapToResponse(MenuItem item) {
        return MenuItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .available(item.isAvailable())
                .build();
    }
}
