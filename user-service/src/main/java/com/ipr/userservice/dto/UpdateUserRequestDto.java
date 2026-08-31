package com.ipr.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequestDto (
        Long id,

        @NotBlank(message = "First name is mandatory")
        String firstName,

        @NotBlank(message = "Last name is mandatory")
        String lastName
) {
}
