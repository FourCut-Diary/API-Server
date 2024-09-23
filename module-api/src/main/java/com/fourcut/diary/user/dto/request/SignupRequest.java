package com.fourcut.diary.user.dto.request;

import com.fourcut.diary.client.SocialType;
import com.fourcut.diary.constant.ValidationMessage;
import com.fourcut.diary.user.domain.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SignupRequest(

        @NotNull(message = ValidationMessage.NULL_SOCIAL_TYPE)
        SocialType socialType,

        @NotBlank(message = ValidationMessage.INVALID_AUTHORIZATION_CODE)
        String authorizationCode,

        @NotBlank(message = ValidationMessage.INVALID_FCM_TOKEN)
        String fcmToken,

        @NotBlank(message = ValidationMessage.INVALID_USER_NAME)
        String name,

        @NotBlank(message = ValidationMessage.INVALID_NICKNAME)
        String nickname,

        @NotNull(message = ValidationMessage.NULL_BIRTHDAY)
        LocalDate birthday,

        @NotNull(message = ValidationMessage.NULL_GENDER)
        Gender gender
) {
}
