package com.fourcut.diary.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SocialType {

    APPLE("애플"),
    KAKAO("카카오")
    ;

    private final String platformName;
}
