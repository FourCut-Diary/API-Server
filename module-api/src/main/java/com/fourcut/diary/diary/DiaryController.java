package com.fourcut.diary.diary;

import com.fourcut.diary.config.resolver.UserAuthentication;
import com.fourcut.diary.diary.dto.*;
import com.fourcut.diary.diary.mapper.DiaryResponseMapper;
import com.fourcut.diary.diary.service.DiaryService;
import com.fourcut.diary.diary.service.dto.MonthDiaryDto;
import com.fourcut.diary.user.service.dto.PhotoCaptureInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController implements DiaryControllerSwagger {

    private final DiaryService diaryService;

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

        PhotoCaptureInfoDto photoCaptureInfoDto = diaryService.getTakePhotoInfoByUser(socialId);
        return ResponseEntity.status(HttpStatus.OK).body(diaryResponseMapper.toPhotoCaptureInfoResponse(photoCaptureInfoDto));
    }

    @GetMapping("/month")
    public ResponseEntity<MonthDiaryResponse> getMonthDiary(@UserAuthentication String socialId, String date) {

        MonthDiaryDto monthDiaryDto = diaryService.getMonthDiaryByUser(socialId, LocalDate.parse(date));
        return ResponseEntity.status(HttpStatus.OK).body(diaryResponseMapper.toMonthDiaryResponse(monthDiaryDto));
    }
}
