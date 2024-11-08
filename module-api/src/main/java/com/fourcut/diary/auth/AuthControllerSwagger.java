package com.fourcut.diary.auth;

import com.fourcut.diary.auth.dto.request.TokenRefreshRequest;
import com.fourcut.diary.auth.dto.response.TokenRefreshResponse;
import com.fourcut.diary.config.resolver.UserAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "사용자 인증 & 인가 관련 API 명세서")
public interface AuthControllerSwagger {

    @Operation(summary = "JWT 토큰 재발급")
    ResponseEntity<TokenRefreshResponse> refreshToken(
            @UserAuthentication String socialId,
            @RequestBody @Valid final TokenRefreshRequest request
    );
}
