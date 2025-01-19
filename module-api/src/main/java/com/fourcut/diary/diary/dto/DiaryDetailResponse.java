package com.fourcut.diary.diary.dto;

import java.time.LocalDate;
import java.time.LocalTime;

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

        LocalTime firstTimeSlot,

        LocalTime secondTimeSlot,

        LocalTime thirdTimeSlot,

        LocalTime fourthTimeSlot
) {
}
