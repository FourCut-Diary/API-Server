package com.fourcut.diary.diary.dto;

import java.time.LocalDate;

public record DiaryDetailResponse(

        LocalDate date,

        String imageUrl
) {
}
