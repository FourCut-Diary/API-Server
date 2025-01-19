package com.fourcut.diary.diary;

import com.fourcut.diary.config.resolver.UserAuthentication;
import com.fourcut.diary.diary.dto.DiaryDetailResponse;
import com.fourcut.diary.diary.dto.TodayDiaryResponse;
import com.fourcut.diary.diary.mapper.DiaryResponseMapper;
import com.fourcut.diary.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<DiaryDetailResponse> getDiary(String socialId, String date) {

        return null;
    }
}
