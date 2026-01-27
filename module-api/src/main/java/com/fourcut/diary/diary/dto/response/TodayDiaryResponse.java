package com.fourcut.diary.diary.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

        String fourthComment,

        LocalDateTime firstCaptureTime,

        LocalDateTime secondCaptureTime,

        LocalDateTime thirdCaptureTime,

        LocalDateTime fourthCaptureTime
) {
}
