package com.fourcut.diary.notification;

import com.fourcut.diary.config.resolver.UserAuthentication;
import com.fourcut.diary.notification.dto.response.PictureCaptureInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Notification", description = "사용자 알림 관련 API 명세서")
public interface NotificationControllerSwagger {

    @Operation(summary = "사진찍기 정보 조회")
    ResponseEntity<PictureCaptureInfoResponse> getTakePictureInfo(@UserAuthentication String socialId);
}
