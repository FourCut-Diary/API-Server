package com.fourcut.diary.user;

import com.fourcut.diary.config.resolver.UserAuthentication;
import com.fourcut.diary.user.dto.response.PhotoCaptureInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Notification Time", description = "알림 관련 API 명세서")
public interface NotificationTimeControllerSwagger {

    @Operation(summary = "사진찍기 정보 조회")
    ResponseEntity<PhotoCaptureInfoResponse> getTakePhotoInfo(@UserAuthentication String socialId);
}
