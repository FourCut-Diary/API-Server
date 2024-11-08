package com.fourcut.diary.auth;

import com.fourcut.diary.auth.dto.request.TokenRefreshRequest;
import com.fourcut.diary.auth.dto.response.TokenRefreshResponse;
import com.fourcut.diary.auth.service.AuthService;
import com.fourcut.diary.config.resolver.UserAuthentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerSwagger {

    private final AuthService authService;

    public ResponseEntity<TokenRefreshResponse> refreshToken(
            @UserAuthentication String socialId,
            @RequestBody @Valid final TokenRefreshRequest request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.refreshToken(socialId, request.refreshToken()));
    }
}
