package com.fourcut.diary.user.dto.response;

public record LoginResponse(

        Long userId,

        String accessToken,

        String refreshToken
) {
}
