package com.fourcut.diary.user.dto.request;

import com.fourcut.diary.client.SocialType;
import com.fourcut.diary.constant.ValidationMessage;
import com.fourcut.diary.user.domain.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record SignupRequest(

        @NotNull(message = ValidationMessage.NULL_SOCIAL_TYPE)
        SocialType socialType,

        // authorizationCode 또는 socialId 중 하나는 필수 (커스텀 검증 필요)
        String authorizationCode,

        // 로그인 시 받은 socialId (authorizationCode 대신 사용 가능)
        String socialId,

        @NotBlank(message = ValidationMessage.INVALID_FCM_TOKEN)
        String fcmToken,

        @NotBlank(message = ValidationMessage.INVALID_NICKNAME)
        String nickname,

        @NotNull(message = ValidationMessage.NULL_BIRTHDAY)
        LocalDate birthday,

        @NotNull(message = ValidationMessage.NULL_GENDER)
        Gender gender,

        @NotNull(message = ValidationMessage.NULL_DAILY_START_TIME)
        LocalTime dailyStartTime,

        @NotNull(message = ValidationMessage.NULL_DAILY_END_TIME)
        LocalTime dailyEndTime
) {

    public boolean hasSocialId() {
        return socialId != null && !socialId.isBlank();
    }

    public boolean hasAuthorizationCode() {
        return authorizationCode != null && !authorizationCode.isBlank();
    }
}
