package com.fourcut.diary.user.service.dto;

import java.time.LocalTime;

public record PhotoCaptureInfoDto(

        Integer currentPhotoIndex,

        LocalTime photoCaptureExpiration
) {
}
