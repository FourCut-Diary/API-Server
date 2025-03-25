package com.fourcut.diary.diary.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DiaryPictureRequest(

        @NotNull
        LocalDateTime now,

        @NotBlank
        String imageUrl,

        @NotNull
        @Min(value = 1, message = "Index must be at least 1")
        @Max(value = 4, message = "Index must be at most 4")
        Integer index,

        String comment
) {
}
