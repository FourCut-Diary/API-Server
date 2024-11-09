package com.fourcut.diary.auth.service;

import com.fourcut.diary.auth.domain.token.RefreshToken;
import com.fourcut.diary.auth.service.token.RefreshTokenCreator;
import com.fourcut.diary.auth.service.token.RefreshTokenDeleter;
import com.fourcut.diary.auth.service.token.RefreshTokenRetriever;
import com.fourcut.diary.user.service.UserRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenCreator refreshTokenCreator;
    private final RefreshTokenRetriever refreshTokenRetriever;
    private final RefreshTokenDeleter refreshTokenDeleter;

    private final UserRetriever userRetriever;

    @Transactional
    public void createRefreshToken(String refreshToken, Long refreshTokenExpTime) {

        refreshTokenCreator.saveRefreshToken(refreshToken, refreshTokenExpTime);
    }

    @Transactional
    public void deleteSavedToken(String socialId, String refreshToken) {

        userRetriever.checkExistUser(socialId);
        RefreshToken savedToken = refreshTokenRetriever.findByRefreshToken(refreshToken);
        refreshTokenDeleter.deleteToken(savedToken);
    }
}
