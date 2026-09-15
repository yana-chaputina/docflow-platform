package com.ipr.userservice.dto.user;

import lombok.Builder;

@Builder
public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String status,
        String role
) {

}
