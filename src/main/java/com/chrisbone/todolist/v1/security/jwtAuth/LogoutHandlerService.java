package com.chrisbone.todolist.v1.security.jwtAuth;

import com.chrisbone.todolist.v1.enums.TokenType;
import com.chrisbone.todolist.v1.repositories.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogoutHandlerService implements LogoutHandler {


    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(TokenType.Bearer.name())) {
            // Invalid or missing authorization header
            return;
        }

        final String refreshToken = authHeader.substring(7);

        refreshTokenRepository.findByRefreshToken(refreshToken)
                .ifPresent(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                });

        // Clear authentication context
        SecurityContextHolder.clearContext();
    }
}
