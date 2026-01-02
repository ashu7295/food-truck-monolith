package com.foodtruck.service;

import com.foodtruck.dto.MenuItemRequestDto;
import com.foodtruck.dto.MenuItemResponseDto;

import java.util.List;

public interface MenuItemService {

    MenuItemResponseDto create(MenuItemRequestDto dto);

    MenuItemResponseDto update(Long id, MenuItemRequestDto dto);

    MenuItemResponseDto getById(Long id);

    List<MenuItemResponseDto> getAll();

    void delete(Long id);

    List<MenuItemResponseDto> getAvailableItems();
}
