package com.ipr.orderservice.security;

import com.ipr.orderservice.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    private final JwtUtils jwtUtils;

    public AuthTokenFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("AuthTokenFilter is called for URI: {}", request.getRequestURI());
        try {
            String jwt=jwtUtils.getJwtFromHeader(request);
            if(jwt!=null && jwtUtils.verifyToken(jwt)){
                JwtPrincipal jwtPrincipal = new JwtPrincipal(
                        jwtUtils.getUserIdFromJwtToken(jwt),
                        jwtUtils.getUsernameFromJwtToken(jwt),
                        jwtUtils.getUserRoleFromJwtToken(jwt));
                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(
                                jwtPrincipal,
                        null,
                        jwtPrincipal.getAuthorities());
                logger.debug("Roles from JWT: {}", jwtPrincipal.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            logger.error("Cannot set up JWT Token: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

}
