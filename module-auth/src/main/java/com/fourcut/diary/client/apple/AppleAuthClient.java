package com.fourcut.diary.client.apple;

import com.fourcut.diary.client.apple.dto.AppleOAuth2TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "appleAuthClient", url = "https://appleid.apple.com")
public interface AppleAuthClient {

    @PostMapping("/auth/token")
    AppleOAuth2TokenResponse getOAuth2Token(
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("grant_type") String grantType,
            @RequestParam("code") String code
    );
}
