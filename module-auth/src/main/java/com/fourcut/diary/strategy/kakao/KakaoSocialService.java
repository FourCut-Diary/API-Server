package com.fourcut.diary.strategy.kakao;

import com.fourcut.diary.client.kakao.KakaoAuthClient;
import com.fourcut.diary.client.kakao.KakaoUserClient;
import com.fourcut.diary.client.kakao.dto.KakaoOAuth2TokenResponse;
import com.fourcut.diary.client.kakao.dto.KakaoUserResponse;
import com.fourcut.diary.strategy.SocialStrategy;
import com.fourcut.diary.strategy.dto.SocialLoginRequest;
import com.fourcut.diary.strategy.dto.SocialLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoSocialService implements SocialStrategy {

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${kakao.clientId}")
    private String kakaoClientId;

    @Value("${kakao.redirectUri}")
    private String kakaoRedirectUri;

    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoUserClient kakaoUserClient;

    @Override
    public SocialLoginResponse getSocialInfo(String authorizationCode) {
        String kakaoAccessToken = getOAuth2Authentication(authorizationCode);
        KakaoUserResponse kakaoUser = kakaoUserClient.getUserInformation(kakaoAccessToken);

        return new SocialLoginResponse(kakaoUser.id());
    }

    private String getOAuth2Authentication(
            final String authorizationCode
    ) {
        KakaoOAuth2TokenResponse response = kakaoAuthClient.getOAuth2AccessToken(
                GRANT_TYPE,
                kakaoClientId,
                kakaoRedirectUri,
                authorizationCode
        );
        return "Bearer " + response.accessToken();
    }
}
