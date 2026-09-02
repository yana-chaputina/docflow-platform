package com.ipr.userservice.security;

import com.ipr.userservice.entity.UserRole;
import com.ipr.userservice.entity.UserStatus;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    @Getter
    private final Long userId;
    private final String email;
    private final String password;
    @Getter
    private final UserRole userRole;
    private final UserStatus userStatus;

    public CustomUserDetails(Long userId, String email, String password, UserRole userRole, UserStatus userStatus) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.userStatus = userStatus;
    }

    @Override
    public @NonNull String getUsername() {
        return email;
    }

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + userRole.name())
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonLocked() {
        return userStatus != UserStatus.BLOCKED;
    }

    @Override
    public boolean isEnabled() {
        return userStatus == UserStatus.ACTIVE;
    }


}
