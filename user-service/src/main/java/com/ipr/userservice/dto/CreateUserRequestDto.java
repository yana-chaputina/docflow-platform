package com.ipr.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequestDto(

        @NotBlank(message = "First name is mandatory")
        String firstName,

        @NotBlank(message = "Last name is mandatory")
        String lastName,

        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Password is mandatory")
        String password
) {
}
