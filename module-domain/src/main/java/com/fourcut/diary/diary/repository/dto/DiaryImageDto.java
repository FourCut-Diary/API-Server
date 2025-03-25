package com.fourcut.diary.diary.repository.dto;

import java.time.LocalDate;

public record DiaryImageDto(

        Long id,

        String imageUrl,

        LocalDate date
) {
}
