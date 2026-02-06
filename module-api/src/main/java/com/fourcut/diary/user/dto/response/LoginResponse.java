package com.fourcut.diary.user.dto.response;

public record LoginResponse(

        Long userId,

        String accessToken,

        String refreshToken,

        boolean needsSignup,

        String socialId
) {

    // 기존 사용자 로그인 성공 시
    public static LoginResponse success(Long userId, String accessToken, String refreshToken) {
        return new LoginResponse(userId, accessToken, refreshToken, false, null);
    }

    // 신규 사용자 - 회원가입 필요
    public static LoginResponse needsSignup(String socialId) {
        return new LoginResponse(null, null, null, true, socialId);
    }
}
