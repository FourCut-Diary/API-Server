package com.fourcut.diary.user.service;

import com.fourcut.diary.aws.SnsService;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.domain.notification.NotificationTime;
import com.fourcut.diary.user.service.notification.NotificationTimeRetriever;
import com.fourcut.diary.user.service.notification.NotificationTimeModifier;
import com.fourcut.diary.util.LocalDateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserScheduler {

    private final UserRetriever userRetriever;

    private final NotificationTimeRetriever notificationTimeRetriever;
    private final NotificationTimeModifier notificationTimeUpdater;

    private final SnsService snsService;

    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void userPushNotificationScheduler() {

        LocalDate currentDate = LocalDate.now();
        LocalDateTime currentTime = LocalDateTime.now();

        // 1. 활동 시간이 종료된 사용자에 대한 처리
        List<User> expiredUsers = userRetriever.getUserIdListWithExpiredDailyEndTime(LocalTime.now());
        expiredUsers.forEach(user -> handleExpiredUser(user, currentDate));

        // 2. 현재 시간이 푸시 알림 시간인 사용자들에게 알림 발송
        List<String> userArnEndpointList = notificationTimeRetriever.findAllUserArnEndpointByCurrentTime(currentTime);
        for (String userArnEndpoint : userArnEndpointList) {
            snsService.topicPublish(userArnEndpoint);
        }
    }

    private void handleExpiredUser(User user, LocalDate currentDate) {
        LocalDateTime startTime = calculateStartTime(user, currentDate);
        LocalDateTime endTime = calculateEndTime(user, currentDate);

        List<LocalDateTime> randomPushTimes = LocalDateTimeUtil.generateRandomDateTimes(
                startTime, endTime, Duration.ofHours(2)
        );

        NotificationTime notificationTime = notificationTimeRetriever.findNotificationTimeByUser(user);
        notificationTimeUpdater.setUserDailyNotificationTime(notificationTime, randomPushTimes);
    }

    private LocalDateTime calculateStartTime(User user, LocalDate currentDate) {
        return LocalDateTime.of(currentDate, user.getDailyStartTime());
    }

    private LocalDateTime calculateEndTime(User user, LocalDate currentDate) {
        if (user.getDailyStartTime().isAfter(user.getDailyEndTime())) {
            return LocalDateTime.of(currentDate.plusDays(1), user.getDailyEndTime());
        }
        return LocalDateTime.of(currentDate, user.getDailyEndTime());
    }
}
