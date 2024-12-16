package com.fourcut.diary.user.dto.response;

import java.time.LocalTime;

public record PhotoCaptureInfoResponse(

        Boolean isPossiblePhotoCapture,

        Integer currentPhotoIndex,

        LocalTime photoCaptureExpiration
) {
}
