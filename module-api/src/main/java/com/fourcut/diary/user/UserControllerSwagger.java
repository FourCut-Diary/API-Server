package com.fourcut.diary.user;

import com.fourcut.diary.config.resolver.UserAuthentication;
import com.fourcut.diary.user.dto.request.LoginRequest;
import com.fourcut.diary.user.dto.request.SignupRequest;
import com.fourcut.diary.user.dto.request.TokenRefreshRequest;
import com.fourcut.diary.user.dto.response.LoginResponse;
import com.fourcut.diary.user.dto.response.SignupResponse;
import com.fourcut.diary.user.dto.response.TokenRefreshResponse;
import com.fourcut.diary.user.dto.response.UserProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "사용자 관련 API 명세서")
public interface UserControllerSwagger {

    @Operation(summary = "소셜 회원가입")
    ResponseEntity<SignupResponse> signup(@RequestBody @Valid final SignupRequest request);

    @Operation(summary = "소셜 로그인")
    ResponseEntity<LoginResponse> login(@RequestBody @Valid final LoginRequest request);

    @Operation(summary = "JWT 토큰 재발급")
    ResponseEntity<TokenRefreshResponse> refreshToken(
            @UserAuthentication String socialId,
            @RequestBody @Valid final TokenRefreshRequest request
    );

    @Operation(summary = "유저 프로필 정보 조회")
    ResponseEntity<UserProfileResponse> getUserProfile(@UserAuthentication String socialId);
}
