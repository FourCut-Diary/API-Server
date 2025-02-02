package com.fourcut.diary.diary.dto;

import java.time.LocalTime;

public record PhotoCaptureInfoResponse(

        Boolean isPossiblePhotoCapture,

        Integer currentPhotoIndex,

        LocalTime photoCaptureExpiration
) {
}
