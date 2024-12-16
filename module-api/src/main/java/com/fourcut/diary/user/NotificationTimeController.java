package com.fourcut.diary.user;

import com.fourcut.diary.user.dto.response.PhotoCaptureInfoResponse;
import com.fourcut.diary.user.mapper.NotificationTimeResponseMapper;
import com.fourcut.diary.user.service.dto.PhotoCaptureInfoDto;
import com.fourcut.diary.user.service.notification.NotificationTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification-time")
@RequiredArgsConstructor
public class NotificationTimeController implements NotificationTimeControllerSwagger {

    private final NotificationTimeService notificationTimeService;

    private final NotificationTimeResponseMapper notificationTimeResponseMapper;

    @GetMapping("/photo")
    public ResponseEntity<PhotoCaptureInfoResponse> getTakePhotoInfo(String socialId) {

        PhotoCaptureInfoDto photoCaptureInfoDto = notificationTimeService.getTakePhotoInfoByUser(socialId);
        return ResponseEntity.status(HttpStatus.OK).body(notificationTimeResponseMapper.toPhotoCaptureInfoResponse(photoCaptureInfoDto));
    }
}
