package com.fourcut.diary.service;

import com.fourcut.diary.diary.service.DiaryService;
import com.fourcut.diary.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Service
public class SchedulerService {

    private final DiaryService diaryService;
    private final NotificationService notificationService;

    /**
     * 자정에 다음날의 일기 생성 및 푸시알림 시간 업데이트
     */
    @Scheduled(cron = "59 59 23 * * *")
    public void createNextDayDiaryAndNotificationTime() {
        LocalDate nextDay = LocalDate.now().plusDays(1);
        diaryService.createNextDayDiaries(nextDay);
        notificationService.updateNotificationTime(nextDay);
    }
}
