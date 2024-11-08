package com.fourcut.diary.auth.domain.token;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("refreshToken")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {

    @Id
    private Long id;

    private String refreshToken;

    @TimeToLive
    private Long expiration;

    public static RefreshToken newInstance(Long id, String refreshToken, Long expiration) {
        return RefreshToken.builder()
                .id(id)
                .refreshToken(refreshToken)
                .expiration(expiration)
                .build();
    }

    public static RefreshToken newInstance(String refreshToken, Long expiration) {
        return RefreshToken.builder()
                .refreshToken(refreshToken)
                .expiration(expiration)
                .build();
    }
}
