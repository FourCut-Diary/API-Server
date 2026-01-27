package com.fourcut.diary.strategy.kakao;

import com.fourcut.diary.client.kakao.KakaoUserClient;
import com.fourcut.diary.client.kakao.dto.KakaoUserResponse;
import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.exception.model.BadRequestException;
import com.fourcut.diary.strategy.SocialStrategy;
import com.fourcut.diary.strategy.dto.SocialLoginResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoSocialService implements SocialStrategy {

    private final KakaoUserClient kakaoUserClient;

    @Override
    public SocialLoginResponse getSocialInfo(String accessToken) {
        String bearerToken = "Bearer " + accessToken;

        try {
            KakaoUserResponse kakaoUser = kakaoUserClient.getUserInformation(bearerToken);

            String profileImageUrl = kakaoUser.kakaoAccount() != null
                    && kakaoUser.kakaoAccount().profile() != null
                    ? kakaoUser.kakaoAccount().profile().profileImageUrl()
                    : null;

            if (profileImageUrl != null && profileImageUrl.startsWith("http://")) {
                profileImageUrl = profileImageUrl.replace("http://", "https://");
            }

            return new SocialLoginResponse(
                    kakaoUser.id(),
                    profileImageUrl
            );
        } catch (FeignException exception) {
            throw new BadRequestException(ErrorMessage.INVALID_EXTERNAL_API_DATA);
        }
    }
}
