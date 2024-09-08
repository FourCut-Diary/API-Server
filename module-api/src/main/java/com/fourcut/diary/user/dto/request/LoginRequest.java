package com.fourcut.diary.user.dto.request;

import com.fourcut.diary.client.SocialType;
import com.fourcut.diary.constant.StringConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(

        @NotNull(message = StringConstant.NULL_SOCIAL_TYPE)
        SocialType socialType,

        @NotBlank(message = StringConstant.INVALID_AUTHORIZATION_CODE)
        String authorizationCode
) {
}
