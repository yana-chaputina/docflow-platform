package com.ipr.userservice.dto.auth;

public record AuthResponse(
        String token,
        String refreshToken
) {
}
