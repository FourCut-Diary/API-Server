package com.fourcut.diary.auth.service.token;

import com.fourcut.diary.auth.domain.token.RefreshToken;
import com.fourcut.diary.auth.repository.token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenDeleter {

    private final RefreshTokenRepository refreshTokenRepository;

    public void deleteToken(RefreshToken refreshToken) {

        refreshTokenRepository.delete(refreshToken);
    }
}
