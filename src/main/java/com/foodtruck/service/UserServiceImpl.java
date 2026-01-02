package com.foodtruck.service;

import com.foodtruck.dto.UserRequestDto;
import com.foodtruck.dto.UserResponseDto;
import com.foodtruck.entity.User;
import com.foodtruck.exception.DuplicateResourceException;
import com.foodtruck.exception.ResourceNotFoundException;
import com.foodtruck.repository.UserRepository;
import com.foodtruck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // ================= CREATE USER =================
    @Override
    public UserResponseDto create(UserRequestDto dto) {

        // 1. Business validation
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("User with this email already exists");
        }

        // 2. DTO -> Entity
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword()) // password encoder later
                .role(dto.getRole())
                .active(true)
                .build();

        // 3. Save
        User savedUser = userRepository.save(user);

        // 4. Entity -> Response DTO
        return mapToResponse(savedUser);
    }

    // ================= UPDATE USER =================
    @Override
    public UserResponseDto update(Long id, UserRequestDto dto) {

        // 1. Fetch existing user
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 2. Update allowed fields
        user.setName(dto.getName());
        user.setRole(dto.getRole());

        // 3. Save updated user
        User updatedUser = userRepository.save(user);

        // 4. Map to response
        return mapToResponse(updatedUser);
    }

    // ================= GET USER BY ID =================
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mapToResponse(user);
    }

    // ================= GET ALL USERS =================
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAll() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ================= SOFT DELETE =================
    @Override
    public void deactivate(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setActive(false);
        userRepository.save(user);
    }

    // ================= MAPPER =================
    private UserResponseDto mapToResponse(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }
}

