package com.ipr.userservice.controller;

import com.ipr.userservice.dto.AuthResponse;
import com.ipr.userservice.dto.LoginRequest;
import com.ipr.userservice.security.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request
    ) {
        String token = authService.login( request.email(), request.password());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
