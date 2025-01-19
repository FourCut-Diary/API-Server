package com.fourcut.diary.diary.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DiaryDetailResponse(

        LocalDate date,

        String firstPicture,

        String secondPicture,

        String thirdPicture,

        String fourthPicture,

        String firstComment,

        String secondComment,

        String thirdComment,

        String fourthComment,

        LocalDateTime firstTimeSlot,

        LocalDateTime secondTimeSlot,

        LocalDateTime thirdTimeSlot,

        LocalDateTime fourthTimeSlot
) {
}
