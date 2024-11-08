package com.fourcut.diary.auth.dto.request;

import com.fourcut.diary.constant.ValidationMessage;
import jakarta.validation.constraints.NotBlank;

public record TokenRefreshRequest(

        @NotBlank(message = ValidationMessage.INVALID_REFRESH_TOKEN)
        String refreshToken
) {
}
