package com.fourcut.diary.diary.dto;

import java.time.LocalDate;

public record TodayDiaryResponse(

        Long id,

        LocalDate date,

        String firstPicture,

        String secondPicture,

        String thirdPicture,

        String fourthPicture,

        String firstComment,

        String secondComment,

        String thirdComment,

        String fourthComment
) {
}
