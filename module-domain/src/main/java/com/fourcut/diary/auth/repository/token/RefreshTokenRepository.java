package com.fourcut.diary.auth.repository.token;

import com.fourcut.diary.auth.domain.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
