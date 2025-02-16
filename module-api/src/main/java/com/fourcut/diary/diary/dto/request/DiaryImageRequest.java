package com.fourcut.diary.diary.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DiaryImageRequest(

        @NotBlank
        String imageUrl,

        @NotNull
        Integer index,

        String comment
) {
}
