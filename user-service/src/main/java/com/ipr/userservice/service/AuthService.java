package com.ipr.userservice.service;

import com.ipr.userservice.dto.auth.AuthResponse;
import com.ipr.userservice.security.CustomUserDetails;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    AuthResponse login(String email, String password);

    AuthResponse refreshToken(String requestToken);

    void logout(String requestToken);

    AuthResponse generateTokens(CustomUserDetails userDetails);
}
