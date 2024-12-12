package com.fourcut.diary.aws.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PresignedUrlRequest(

        @NotBlank
        String fileName
) {
}
