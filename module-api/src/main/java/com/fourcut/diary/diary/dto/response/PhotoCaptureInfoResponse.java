package com.fourcut.diary.diary.dto.response;

import java.time.LocalTime;

public record PhotoCaptureInfoResponse(

        Boolean isPossiblePhotoCapture,

        Integer currentPhotoIndex,

        LocalTime photoCaptureExpiration
) {
}
