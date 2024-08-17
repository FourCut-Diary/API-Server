package com.fourcut.diary.strategy;

import com.fourcut.diary.client.SocialType;
import com.fourcut.diary.strategy.kakao.KakaoSocialService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SocialStrategyProvider {

    private static final Map<SocialType, SocialStrategy> socialServiceMap = new HashMap<>();

    private final KakaoSocialService kakaoSocialService;

    @PostConstruct
    void initializeSocialServiceMap() {
        socialServiceMap.put(SocialType.KAKAO, kakaoSocialService);
    }

    public SocialStrategy getSocialService(SocialType socialType) {
        return socialServiceMap.get(socialType);
    }
}
