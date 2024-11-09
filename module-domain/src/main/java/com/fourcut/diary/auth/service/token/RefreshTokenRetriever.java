package com.fourcut.diary.auth.service.token;

import com.fourcut.diary.auth.domain.token.RefreshToken;
import com.fourcut.diary.auth.repository.token.RefreshTokenRepository;
import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.exception.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenRetriever {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {

        return refreshTokenRepository.findByRefreshTokenValue(refreshToken)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.INVALID_JWT_TOKEN));
    }
}
