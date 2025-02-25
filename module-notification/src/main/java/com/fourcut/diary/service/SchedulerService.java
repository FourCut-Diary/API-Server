package com.fourcut.diary.service;

import com.fourcut.diary.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class SchedulerService {

    private final DiaryService diaryService;

    /**
     * 매분마다 사용자의 일기가 만료됐는지 확인
     */
    @Scheduled(cron = "0 * * * * *")
    public void setRandomNextDayPushNotificationTime() {
        LocalDateTime now = LocalDateTime.now();
        diaryService.createNextDayDiaries(now);
    }
}
