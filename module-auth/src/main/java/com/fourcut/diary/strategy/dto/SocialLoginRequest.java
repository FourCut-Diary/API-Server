package com.fourcut.diary.strategy.dto;

import com.fourcut.diary.constant.ValidationMessage;
import jakarta.validation.constraints.NotBlank;

public record SocialLoginRequest(

        @NotBlank(message = ValidationMessage.INVALID_AUTHORIZATION_CODE)
        String authorizationCode
) {
}
