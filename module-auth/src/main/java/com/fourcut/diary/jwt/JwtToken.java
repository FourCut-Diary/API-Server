package com.fourcut.diary.jwt;

public record JwtToken(

        String accessToken,

        String refreshToken
) {
}
