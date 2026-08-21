package com.ipr.userservice.dto;

public record UpdateUserRequestDto (
        Long id,
        String firstName,
        String lastName
) {
}
