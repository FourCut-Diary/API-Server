package com.fourcut.diary.notification.mapper;

import com.fourcut.diary.notification.dto.response.PictureCaptureInfoResponse;
import com.fourcut.diary.user.service.dto.PictureCaptureInfoDto;
import org.springframework.stereotype.Component;

@Component
public class NotificationResponseMapper {

    public PictureCaptureInfoResponse toPhotoCaptureInfoResponse(PictureCaptureInfoDto dto) {
        boolean isCapturePossible = dto.currentPhotoIndex() != -1;
        return new PictureCaptureInfoResponse(
                isCapturePossible,
                isCapturePossible ? dto.currentPhotoIndex() : null,
                isCapturePossible ? dto.photoCaptureExpiration() : null
        );
    }
}
