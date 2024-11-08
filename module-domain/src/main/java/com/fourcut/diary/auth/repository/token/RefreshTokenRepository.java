package com.fourcut.diary.auth.repository.token;

import com.fourcut.diary.auth.domain.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String>, RefreshTokenRepositoryCustom {

}
