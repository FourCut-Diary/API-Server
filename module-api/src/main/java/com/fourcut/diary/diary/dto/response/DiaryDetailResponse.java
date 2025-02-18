package com.fourcut.diary.diary.dto.response;

import java.time.LocalDate;

public record DiaryDetailResponse(

        LocalDate date,

        String imageUrl
) {
}
