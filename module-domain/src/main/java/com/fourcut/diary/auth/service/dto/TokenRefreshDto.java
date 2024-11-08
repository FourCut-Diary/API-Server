package com.fourcut.diary.auth.service.dto;

public record TokenRefreshDto(

        String accessToken,

        String refreshToken
) {
}
