package com.fourcut.diary.user.dto.request;

import com.fourcut.diary.client.SocialType;
import com.fourcut.diary.constant.StringConstant;
import com.fourcut.diary.user.domain.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SignupRequest(

        @NotNull(message = StringConstant.NULL_SOCIAL_TYPE)
        SocialType socialType,

        @NotBlank(message = StringConstant.INVALID_AUTHORIZATION_CODE)
        String authorizationCode,

        @NotBlank(message = StringConstant.INVALID_FCM_TOKEN)
        String fcmToken,

        @NotBlank(message = StringConstant.INVALID_USER_NAME)
        String name,

        @NotBlank(message = StringConstant.INVALID_NICKNAME)
        String nickname,

        @NotNull(message = StringConstant.NULL_BIRTHDAY)
        LocalDate birthday,

        @NotNull(message = StringConstant.NULL_GENDER)
        Gender gender
) {
}