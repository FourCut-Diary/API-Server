package com.fourcut.diary.strategy;

import com.fourcut.diary.strategy.dto.SocialLoginResponse;

public interface SocialStrategy {

    public SocialLoginResponse getSocialInfo(String authorizationCode);
}
