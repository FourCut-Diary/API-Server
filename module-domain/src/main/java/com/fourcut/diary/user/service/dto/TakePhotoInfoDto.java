package com.fourcut.diary.user.service.dto;

import java.time.LocalTime;

public record TakePhotoInfoDto(

        Boolean isPossiblePhotoCapture,

        LocalTime photoCaptureExpiration,

        Integer photoNumber
) {
}
