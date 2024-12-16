package com.fourcut.diary.user.mapper;

import com.fourcut.diary.user.dto.response.PhotoCaptureInfoResponse;
import com.fourcut.diary.user.service.dto.PhotoCaptureInfoDto;
import org.springframework.stereotype.Component;

@Component
public class NotificationTimeResponseMapper {

    public PhotoCaptureInfoResponse toPhotoCaptureInfoResponse(PhotoCaptureInfoDto dto) {
        boolean isCapturePossible = dto.currentPhotoIndex() != -1;
        return new PhotoCaptureInfoResponse(
                isCapturePossible,
                isCapturePossible ? dto.currentPhotoIndex() : null,
                isCapturePossible ? dto.photoCaptureExpiration() : null
        );
    }
}
