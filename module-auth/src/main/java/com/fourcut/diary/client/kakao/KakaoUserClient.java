package com.fourcut.diary.client.kakao;

import com.fourcut.diary.client.kakao.dto.KakaoUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoUserClient", url = "https://kapi.kakao.com")
public interface KakaoUserClient {

    @GetMapping(value = "/v2/user/me")
    KakaoUserResponse getUserInformation(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken
    );
}
