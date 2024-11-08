package com.fourcut.diary.auth.service;

import com.fourcut.diary.auth.service.token.RefreshTokenCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenCreator refreshTokenCreator;

    @Transactional
    public void createRefreshToken(String refreshToken, Long refreshTokenExpTime) {

        refreshTokenCreator.saveRefreshToken(refreshToken, refreshTokenExpTime);
    }
}
