package com.fourcut.diary.notification.dto.response;

import java.time.LocalTime;

public record PictureCaptureInfoResponse(

        Boolean isPossiblePhotoCapture,

        Integer currentPhotoIndex,

        LocalTime photoCaptureExpiration
) {
}
