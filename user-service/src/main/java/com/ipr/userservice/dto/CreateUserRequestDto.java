package com.ipr.userservice.dto;

public record CreateUserRequestDto(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
