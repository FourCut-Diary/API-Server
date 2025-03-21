package com.fourcut.diary.notification;

import com.fourcut.diary.config.resolver.UserAuthentication;
import com.fourcut.diary.notification.dto.response.PictureCaptureInfoResponse;
import com.fourcut.diary.notification.mapper.NotificationResponseMapper;
import com.fourcut.diary.notification.service.NotificationService;
import com.fourcut.diary.user.service.dto.PictureCaptureInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController implements NotificationControllerSwagger {

    private final NotificationService notificationService;

    private final NotificationResponseMapper notificationResponseMapper;

    @GetMapping("/info")
    public ResponseEntity<PictureCaptureInfoResponse> getTakePictureInfo(@UserAuthentication String socialId) {

        PictureCaptureInfoDto pictureCaptureInfoDto = notificationService.getTakePictureInfoByUser(socialId);
        return ResponseEntity.status(HttpStatus.OK).body(notificationResponseMapper.toPhotoCaptureInfoResponse(pictureCaptureInfoDto));
    }
}
