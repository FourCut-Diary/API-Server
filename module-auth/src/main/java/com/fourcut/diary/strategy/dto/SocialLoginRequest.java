package com.fourcut.diary.strategy.dto;

import com.fourcut.diary.constant.StringConstant;
import jakarta.validation.constraints.NotBlank;

public record SocialLoginRequest(

        @NotBlank(message = StringConstant.INVALID_AUTHORIZATION_CODE)
        String authorizationCode
) {
}
