package com.fourcut.diary.auth.service;

import com.fourcut.diary.auth.domain.RefreshToken;
import com.fourcut.diary.auth.repository.RefreshTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenCreator {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(String refreshToken, Long refreshTokenExpTime) {

        refreshTokenRepository.save(new RefreshToken(refreshToken, refreshTokenExpTime));
    }
}
