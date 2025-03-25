package com.fourcut.diary.diary.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DiaryRequest(

        @NotBlank
        String imageUrl,

        @Size(max = 20)
        String title
) {
}
