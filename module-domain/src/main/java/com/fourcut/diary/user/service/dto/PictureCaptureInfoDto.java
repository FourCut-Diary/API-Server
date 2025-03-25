package com.fourcut.diary.user.service.dto;

import java.time.LocalTime;

public record PictureCaptureInfoDto(

        Integer currentPhotoIndex,

        LocalTime photoCaptureExpiration
) {
}
