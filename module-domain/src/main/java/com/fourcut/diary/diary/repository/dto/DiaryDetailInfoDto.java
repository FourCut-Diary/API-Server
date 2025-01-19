package com.fourcut.diary.diary.repository.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DiaryDetailInfoDto(

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
){
}
