package com.fourcut.diary.user.facade;

import com.fourcut.diary.auth.service.AuthService;
import com.fourcut.diary.aws.EventBridgeService;
import com.fourcut.diary.aws.SnsService;
import com.fourcut.diary.client.SocialType;
import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.exception.model.BadRequestException;
import com.fourcut.diary.exception.model.NotFoundException;
import com.fourcut.diary.jwt.JwtToken;
import com.fourcut.diary.jwt.JwtTokenManager;
import com.fourcut.diary.notification.service.NotificationService;
import com.fourcut.diary.strategy.SocialStrategy;
import com.fourcut.diary.strategy.SocialStrategyProvider;
import com.fourcut.diary.strategy.dto.SocialLoginResponse;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.dto.request.LoginRequest;
import com.fourcut.diary.user.dto.request.SignupRequest;
import com.fourcut.diary.user.dto.request.TokenRefreshRequest;
import com.fourcut.diary.user.dto.response.LoginResponse;
import com.fourcut.diary.user.dto.response.SignupResponse;
import com.fourcut.diary.user.dto.response.TokenRefreshResponse;
import com.fourcut.diary.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthFacade {

    private final JwtTokenManager jwtTokenManager;
    private final SocialStrategyProvider socialStrategyProvider;

    private final AuthService authService;
    private final UserService userService;
    private final NotificationService notificationService;
    private final EventBridgeService eventBridgeService;
    private final SnsService snsService;

    public SignupResponse signup(SignupRequest request) {
        String socialId;
        String profileImageUrl = null;

        if (request.hasSocialId()) {
            socialId = request.socialId();
        }

        else if (request.hasAuthorizationCode()) {
            SocialLoginResponse socialLoginResponse = getSocialInfo(request.socialType(), request.authorizationCode());
            socialId = socialLoginResponse.socialId();
            profileImageUrl = socialLoginResponse.profileImageUrl();
        } else {
            throw new BadRequestException(ErrorMessage.MISSING_AUTH_CREDENTIALS);
        }

        String snsEndpointArd = snsService.createEndpoint(request.fcmToken());
        Long id = userService.createUser(
                socialId,
                request.nickname(),
                request.birthday(),
                request.gender(),
                request.dailyStartTime(),
                request.dailyEndTime(),
                profileImageUrl,
                snsEndpointArd,
                request.fcmToken()
        );

        JwtToken jwtToken = getJwtToken(socialId);
        List<LocalDateTime> timeSlot = notificationService.createNotification(socialId);
        eventBridgeService.enrollPushNotificationScheduler(id, timeSlot);

        return new SignupResponse(id, jwtToken.accessToken(), jwtToken.refreshToken());
    }

    public LoginResponse login(LoginRequest request) {
        SocialLoginResponse socialLoginResponse = getSocialInfo(request.socialType(), request.authorizationCode());

        try {
            User loginUser = userService.getUserBySocialId(socialLoginResponse.socialId());
            if (loginUser.isDifferentFcmToken(request.fcmToken())) {
                String newSnsEndpointArn = snsService.createEndpoint(request.fcmToken());
                loginUser.updateFcmToken(request.fcmToken());
                loginUser.updateSnsEndpoint(newSnsEndpointArn);
            }
            JwtToken jwtToken = getJwtToken(loginUser.getSocialId());

            return LoginResponse.success(loginUser.getId(), jwtToken.accessToken(), jwtToken.refreshToken());
        } catch (NotFoundException e) {
            return LoginResponse.needsSignup(socialLoginResponse.socialId());
        }
    }

    public TokenRefreshResponse refreshToken(String socialId, TokenRefreshRequest request) {
        authService.deleteSavedToken(socialId, request.refreshToken());
        JwtToken jwtToken = getJwtToken(socialId);

        return new TokenRefreshResponse(jwtToken.accessToken(), jwtToken.refreshToken());
    }

    private SocialLoginResponse getSocialInfo(SocialType socialType, String authorizationCode) {
        SocialStrategy socialStrategy = socialStrategyProvider.getSocialService(socialType);
        return socialStrategy.getSocialInfo(authorizationCode);
    }

    private JwtToken getJwtToken(String socialId) {
        return jwtTokenManager.getUserJwtToken(socialId);
    }
}
