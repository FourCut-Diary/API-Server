package com.fourcut.diary.diary;

import com.fourcut.diary.config.resolver.UserAuthentication;
import com.fourcut.diary.diary.dto.request.DiaryPictureRequest;
import com.fourcut.diary.diary.dto.response.DiaryDetailResponse;
import com.fourcut.diary.diary.dto.response.MonthDiaryResponse;
import com.fourcut.diary.diary.dto.response.PhotoCaptureInfoResponse;
import com.fourcut.diary.diary.dto.response.TodayDiaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Tag(name = "Diary", description = "일기 관련 API 명세서")
public interface DiaryControllerSwagger {

    @Operation(summary = "오늘 일기 조회")
    ResponseEntity<TodayDiaryResponse> getTodayDiary(@UserAuthentication String socialId);

    @Operation(summary = "특정 일자 일기 상세조회")
    ResponseEntity<DiaryDetailResponse> getDiary(@UserAuthentication String socialId, @RequestParam String date);

    @Operation(summary = "사진찍기 정보 조회")
    ResponseEntity<PhotoCaptureInfoResponse> getTakePhotoInfo(@UserAuthentication String socialId);

    @Operation(summary = "월별 일기 조회")
    ResponseEntity<MonthDiaryResponse> getMonthDiary(@UserAuthentication String socialId, @RequestParam String date);

    @Operation(summary = "오늘의 순간 사진 등록")
    ResponseEntity<Map<String, Boolean>> enrollPicture(@UserAuthentication String socialId, @RequestBody DiaryPictureRequest request);
}
