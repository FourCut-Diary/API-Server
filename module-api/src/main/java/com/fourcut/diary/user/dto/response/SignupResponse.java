package com.fourcut.diary.user.dto.response;

public record SignupResponse(

        Long userId,

        String accessToken,

        String refreshToken
) {
}
