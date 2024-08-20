package com.fourcut.diary.user;

import com.fourcut.diary.strategy.SocialStrategy;
import com.fourcut.diary.strategy.SocialStrategyProvider;
import com.fourcut.diary.strategy.dto.SocialLoginResponse;
import com.fourcut.diary.strategy.dto.SocialLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final SocialStrategyProvider socialStrategyProvider;

    @PostMapping("/social-login")
    public ResponseEntity<SocialLoginResponse> socialLogin(final SocialLoginRequest request) {
        SocialStrategy socialStrategy = socialStrategyProvider.getSocialService(request.socialType());

        return ResponseEntity.status(HttpStatus.OK).body(socialStrategy.login(request));
    }
}
