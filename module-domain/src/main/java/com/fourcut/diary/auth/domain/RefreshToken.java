package com.fourcut.diary.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("RedisToken")
@AllArgsConstructor
@Getter
public class RefreshToken {

    @Id
    private Long id;

    private String refreshToken;

    @TimeToLive
    private Long expiration;

    public RefreshToken(
            String refreshToken,
            Long refreshTokenExpiration
    ) {
        this.refreshToken = refreshToken;
        this.expiration = refreshTokenExpiration;
    }
}
