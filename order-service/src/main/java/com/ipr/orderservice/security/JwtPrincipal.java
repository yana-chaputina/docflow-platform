package com.ipr.orderservice.security;

import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtPrincipal {

    @Getter
    private final Long userId;
    private final String email;
    @Getter
    private final String userRole;

    public JwtPrincipal(Long userId, String email, String userRole) {
        this.userId = userId;
        this.email = email;
        this.userRole = userRole;
    }

    public @NonNull String getUsername() {
        return email;
    }

    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + userRole)
        );
    }




}
