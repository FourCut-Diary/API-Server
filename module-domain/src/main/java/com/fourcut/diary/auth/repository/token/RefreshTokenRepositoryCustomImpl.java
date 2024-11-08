package com.fourcut.diary.auth.repository.token;

import com.fourcut.diary.auth.domain.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class RefreshTokenRepositoryCustomImpl implements RefreshTokenRepositoryCustom {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Optional<RefreshToken> findByRefreshTokenValue(String refreshToken) {

        Set<String> keys = redisTemplate.keys("refreshToken:*");
        if (keys == null) {
            return Optional.empty();
        }

        for (String key : keys) {
            Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
            if (refreshToken.equals(entries.get("refreshToken"))) {
                return Optional.of(RefreshToken.newInstance(
                        Long.valueOf((String) entries.get("id")),
                        (String) entries.get("refreshToken"),
                        Long.valueOf((String) entries.get("expiration"))
                ));
            }
        }

        return Optional.empty();
    }
}
