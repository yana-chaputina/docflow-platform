package com.ipr.userservice.service;

import com.ipr.userservice.dto.auth.AuthResponse;
import com.ipr.userservice.entity.RefreshToken;
import com.ipr.userservice.entity.User;
import com.ipr.userservice.jwt.JwtUtils;
import com.ipr.userservice.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl (
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils, RefreshTokenService refreshTokenService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public AuthResponse login(String email, String password) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                email,
                                password
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return generateTokens(userDetails);

    }

    @Override
    @Transactional
    public AuthResponse refreshToken(String requestToken) {
        RefreshToken refreshToken=refreshTokenService.findByToken(requestToken);
        if(refreshTokenService.isTokenExpired(refreshToken)) {
            refreshTokenService.delete(refreshToken);
            throw new RuntimeException("Refresh token was expired. Log in again.");
        }
        User user=refreshToken.getUser();
        return generateTokens(new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRole(),
                user.getStatus()
        ));
    }

    @Override
    public void logout(String requestToken) {
        RefreshToken refreshToken=refreshTokenService.findByToken(requestToken);
        refreshTokenService.delete(refreshToken);
    }

    @Override
    public AuthResponse generateTokens(CustomUserDetails userDetails)
    {
        String token=jwtUtils.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
        return new AuthResponse(token,refreshToken.getToken());
    }
}
