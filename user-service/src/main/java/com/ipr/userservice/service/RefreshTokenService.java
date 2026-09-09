package com.ipr.userservice.service;

import com.ipr.userservice.entity.RefreshToken;
import org.springframework.stereotype.Service;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String email);

    RefreshToken findByToken(String token);

    boolean isTokenExpired(RefreshToken token);

    void delete(RefreshToken refreshToken);
}
