package com.fourcut.diary.auth.repository.token;

import com.fourcut.diary.auth.domain.token.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepositoryCustom {

    Optional<RefreshToken> findByRefreshTokenValue(String refreshToken);
}
