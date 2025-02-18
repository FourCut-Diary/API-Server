package com.fourcut.diary.diary.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DiaryRequest(

        @NotNull
        String imageUrl,

        @Size(max = 20)
        String title
) {
}
