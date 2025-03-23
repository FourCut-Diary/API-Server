package com.fourcut.diary.service;

import com.fourcut.diary.aws.EventBridgeService;
import com.fourcut.diary.diary.service.DiaryService;
import com.fourcut.diary.notification.domain.Notification;
import com.fourcut.diary.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SchedulerService {

    private final DiaryService diaryService;
    private final NotificationService notificationService;
    private final EventBridgeService eventBridgeService;

    /**
     * 자정에 다음날의 일기 생성 및 푸시알림 시간 업데이트
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void createNextDayDiaryAndNotificationTime() {
        // 3일 이상 지난 EventBridge Scheduler 삭제
        eventBridgeService.deletePastSchedules();

        LocalDate nextDay = LocalDate.now();
        diaryService.createNextDayDiaries(nextDay);
        notificationService.updateNotificationTime(nextDay);

        // 다음날 푸시알림 시간 EventBridge Scheduler 적용
        List<Notification> notifications = notificationService.getAllNotifications();
        for (Notification notification : notifications) {
            List<LocalDateTime> timeSlot = new ArrayList<>();
            timeSlot.add(notification.getFirstTimeSlot());
            timeSlot.add(notification.getSecondTimeSlot());
            timeSlot.add(notification.getThirdTimeSlot());
            timeSlot.add(notification.getFourthTimeSlot());

            eventBridgeService.enrollPushNotificationScheduler(notification.getUser().getId(), timeSlot);
        }
    }
}
