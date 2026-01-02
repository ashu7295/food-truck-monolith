package com.foodtruck.controller;

import com.foodtruck.dto.UserRequestDto;
import com.foodtruck.dto.UserResponseDto;
import com.foodtruck.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "User management APIs")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ================= CREATE USER =================
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody UserRequestDto dto) {

        UserResponseDto response = userService.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ================= UPDATE USER =================
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto dto) {

        UserResponseDto response = userService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    // ================= GET USER BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {

        UserResponseDto response = userService.getById(id);
        return ResponseEntity.ok(response);
    }

    // ================= GET ALL USERS =================
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {

        List<UserResponseDto> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    // ================= SOFT DELETE USER =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {

        userService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
