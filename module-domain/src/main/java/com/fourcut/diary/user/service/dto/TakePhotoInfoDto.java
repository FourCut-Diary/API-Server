package com.fourcut.diary.user.service.dto;

import java.time.LocalTime;

public record TakePhotoInfoDto(

        Integer currentPhotoIndex,

        LocalTime photoCaptureExpiration
) {
}
