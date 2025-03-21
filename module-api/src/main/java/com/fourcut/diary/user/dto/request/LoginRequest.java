package com.fourcut.diary.user.dto.request;

import com.fourcut.diary.client.SocialType;
import com.fourcut.diary.constant.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(

        @NotNull(message = ValidationMessage.NULL_SOCIAL_TYPE)
        SocialType socialType,

        @NotBlank(message = ValidationMessage.INVALID_AUTHORIZATION_CODE)
        String authorizationCode,

        @NotBlank(message = ValidationMessage.INVALID_FCM_TOKEN)
        String fcmToken
) {
}
