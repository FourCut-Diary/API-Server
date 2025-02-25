package com.fourcut.diary.diary;

import com.fourcut.diary.aws.S3Service;
import com.fourcut.diary.config.resolver.UserAuthentication;
import com.fourcut.diary.diary.dto.request.DiaryPictureRequest;
import com.fourcut.diary.diary.dto.request.DiaryRequest;
import com.fourcut.diary.diary.dto.response.DiaryDetailResponse;
import com.fourcut.diary.diary.dto.response.MonthDiaryResponse;
import com.fourcut.diary.diary.dto.response.PhotoCaptureInfoResponse;
import com.fourcut.diary.diary.dto.response.TodayDiaryResponse;
import com.fourcut.diary.diary.mapper.DiaryResponseMapper;
import com.fourcut.diary.diary.service.DiaryService;
import com.fourcut.diary.diary.service.dto.MonthDiaryDto;
import com.fourcut.diary.user.service.dto.PictureCaptureInfoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController implements DiaryControllerSwagger {

    private final DiaryService diaryService;
    private final S3Service s3Service;

    private final DiaryResponseMapper diaryResponseMapper;

    @GetMapping("/today")
    public ResponseEntity<TodayDiaryResponse> getTodayDiary(@UserAuthentication String socialId) {

        return ResponseEntity.status(HttpStatus.OK).body(diaryResponseMapper.toTodayDiaryResponse(diaryService.getTodayDiary(socialId)));
    }

    @GetMapping("/detail")
    public ResponseEntity<DiaryDetailResponse> getDiary(@UserAuthentication String socialId, String date) {

        return ResponseEntity.status(HttpStatus.OK).body(
                diaryResponseMapper.toDiaryDetailResponse(diaryService.getDiary(socialId, LocalDate.parse(date)))
        );
    }

    @GetMapping("/photo")
    public ResponseEntity<PhotoCaptureInfoResponse> getTakePhotoInfo(@UserAuthentication String socialId) {

        PictureCaptureInfoDto pictureCaptureInfoDto = diaryService.getTakePictureInfoByUser(socialId);
        return ResponseEntity.status(HttpStatus.OK).body(diaryResponseMapper.toPhotoCaptureInfoResponse(pictureCaptureInfoDto));
    }

    @GetMapping("/month")
    public ResponseEntity<MonthDiaryResponse> getMonthDiary(@UserAuthentication String socialId, String date) {

        MonthDiaryDto monthDiaryDto = diaryService.getMonthDiaryByUser(socialId, LocalDate.parse(date));
        return ResponseEntity.status(HttpStatus.OK).body(diaryResponseMapper.toMonthDiaryResponse(monthDiaryDto));
    }

    @PutMapping("/picture")
    public ResponseEntity<Map<String, Boolean>> enrollPicture(@UserAuthentication String socialId, @RequestBody @Valid final DiaryPictureRequest request) {

        s3Service.checkImageUrlExists(request.imageUrl());
        diaryService.enrollPictureInDiary(socialId, request.now(), request.imageUrl(), request.index(), request.comment());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", true));
    }

    @PutMapping("/enroll")
    public ResponseEntity<Map<String, Boolean>> enrollDiary(@UserAuthentication String socialId, @RequestBody @Valid final DiaryRequest request) {

        s3Service.checkImageUrlExists(request.imageUrl());
        diaryService.enrollDiary(socialId, request.imageUrl(), request.title());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", true));
    }
}
