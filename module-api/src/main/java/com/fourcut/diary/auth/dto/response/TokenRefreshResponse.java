package com.fourcut.diary.auth.dto.response;

public record TokenRefreshResponse(

        String accessToken,

        String refreshToken
) {
}
