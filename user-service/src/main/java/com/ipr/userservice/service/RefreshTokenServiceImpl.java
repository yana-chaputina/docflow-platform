package com.ipr.userservice.service;

import com.ipr.userservice.entity.RefreshToken;
import com.ipr.userservice.repository.RefreshTokenRepository;
import com.ipr.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refresh.token.expiration}")
    private Long refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository repo, UserRepository userRepo) {
        this.refreshTokenRepository = repo;
        this.userRepository = userRepo;
    }

    @Override
    public RefreshToken createRefreshToken(String email) {
        var token = new RefreshToken();
        token.setUser(userRepository.findByEmail(email).get());
        token.setExpiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiration));
        token.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(token);
    }

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }

    @Override
    public boolean isTokenExpired(RefreshToken token) {
        return token.getExpiresAt().isBefore(LocalDateTime.now());
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }
}
