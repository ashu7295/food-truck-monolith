package com.foodtruck.service;

import com.foodtruck.dto.UserRequestDto;
import com.foodtruck.dto.UserResponseDto;

import java.util.List;

import java.util.List;

public interface UserService {

    UserResponseDto create(UserRequestDto dto);

    UserResponseDto update(Long id, UserRequestDto dto);

    UserResponseDto getById(Long id);

    List<UserResponseDto> getAll();

    void deactivate(Long id);
}

