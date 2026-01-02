package com.foodtruck.dto;

import com.foodtruck.enums.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private boolean active;
}
