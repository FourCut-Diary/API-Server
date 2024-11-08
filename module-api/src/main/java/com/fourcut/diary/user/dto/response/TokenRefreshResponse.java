package com.fourcut.diary.user.dto.response;

public record TokenRefreshResponse(

        String accessToken,

        String refreshToken
) {
}
