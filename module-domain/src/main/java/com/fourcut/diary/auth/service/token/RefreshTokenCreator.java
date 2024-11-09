package com.fourcut.diary.auth.service.token;

import com.fourcut.diary.auth.domain.token.RefreshToken;
import com.fourcut.diary.auth.repository.token.RefreshTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenCreator {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(String refreshToken, Long refreshTokenExpTime) {

        refreshTokenRepository.save(RefreshToken.newInstance(refreshToken, refreshTokenExpTime));
    }
}
