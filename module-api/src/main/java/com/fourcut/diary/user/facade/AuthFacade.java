package com.fourcut.diary.user.facade;

import com.fourcut.diary.aws.SnsService;
import com.fourcut.diary.client.SocialType;
import com.fourcut.diary.jwt.JwtToken;
import com.fourcut.diary.jwt.JwtTokenManager;
import com.fourcut.diary.strategy.SocialStrategy;
import com.fourcut.diary.strategy.SocialStrategyProvider;
import com.fourcut.diary.strategy.dto.SocialLoginResponse;
import com.fourcut.diary.user.domain.User;
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

    private final JwtTokenManager jwtTokenManager;
    private final SocialStrategyProvider socialStrategyProvider;

    private final UserService userService;

    private final SnsService snsService;

    public SignupResponse signup(SignupRequest request) {
        SocialLoginResponse socialLoginResponse = getSocialInfo(request.socialType(), request.authorizationCode());

        String snsArnEndpoint = snsService.createEndpoint(request.fcmToken());
        String endpoint = snsArnEndpoint.split("/")[3];
        Long id = userService.createUser(
                socialLoginResponse.socialId(),
                request.nickname(),
                request.birthday(),
                request.gender(),
                request.dailyStartTime(),
                request.dailyEndTime(),
                endpoint
        );

        JwtToken jwtToken = getJwtToken(socialLoginResponse.socialId());

        return new SignupResponse(id, jwtToken.accessToken(), jwtToken.refreshToken());
    }

    public LoginResponse login(LoginRequest request) {
        SocialLoginResponse socialLoginResponse = getSocialInfo(request.socialType(), request.authorizationCode());
        User loginUser = userService.getUserBySocialId(socialLoginResponse.socialId());
        JwtToken jwtToken = getJwtToken(loginUser.getSocialId());

        return new LoginResponse(loginUser.getId(), jwtToken.accessToken(), jwtToken.refreshToken());
    }

    private SocialLoginResponse getSocialInfo(SocialType socialType, String authorizationCode) {
        SocialStrategy socialStrategy = socialStrategyProvider.getSocialService(socialType);
        return socialStrategy.getSocialInfo(authorizationCode);
    }

    private JwtToken getJwtToken(String socialId) {
        return jwtTokenManager.getUserJwtToken(socialId);
    }
}
