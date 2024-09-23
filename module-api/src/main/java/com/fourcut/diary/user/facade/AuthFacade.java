package com.fourcut.diary.user.facade;

import com.fourcut.diary.aws.LambdaService;
import com.fourcut.diary.client.SocialType;
import com.fourcut.diary.strategy.SocialStrategy;
import com.fourcut.diary.strategy.SocialStrategyProvider;
import com.fourcut.diary.strategy.dto.SocialLoginResponse;
import com.fourcut.diary.user.dto.request.LoginRequest;
import com.fourcut.diary.user.dto.request.SignupRequest;
import com.fourcut.diary.user.dto.response.LoginResponse;
import com.fourcut.diary.user.dto.response.SignupResponse;
import com.fourcut.diary.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthFacade {

    private final SocialStrategyProvider socialStrategyProvider;

    private final UserService userService;

    private final LambdaService lambdaService;

    public SignupResponse signup(SignupRequest request) {
        SocialLoginResponse socialLoginResponse = getSocialInfo(request.socialType(), request.authorizationCode());

        Long id = userService.createUser(socialLoginResponse.socialId(), request.name(), request.nickname(), request.birthday(), request.gender());
        lambdaService.enrollFcmTokenInSNS(request.fcmToken());
        return new SignupResponse(id, "accessToken", "refreshToken");
    }

    public LoginResponse login(LoginRequest request) {
        SocialLoginResponse socialLoginResponse = getSocialInfo(request.socialType(), request.authorizationCode());

        return new LoginResponse(userService.getUser(socialLoginResponse.socialId()), "accessToken", "refreshToken");
    }

    private SocialLoginResponse getSocialInfo(SocialType socialType, String authorizationCode) {
        SocialStrategy socialStrategy = socialStrategyProvider.getSocialService(socialType);
        return socialStrategy.getSocialInfo(authorizationCode);
    }
}
