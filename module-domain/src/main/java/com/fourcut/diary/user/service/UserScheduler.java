package com.fourcut.diary.user.service;

import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.domain.notification.NotificationTime;
import com.fourcut.diary.user.service.notification.NotificationTimeRetriever;
import com.fourcut.diary.user.service.notification.NotificationTimeUpdater;
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
    private final NotificationTimeUpdater notificationTimeUpdater;

    /*
    사용자가 설정한 활동 시간이 종료되면 다음 날의 푸시알림 시간이 설정됩니다.
     */
    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void setUserDailyPushNotificationTime() {

        List<User> users = userRetriever.getUserIdListWithExpiredDailyEndTime(LocalTime.now());
        if (!users.isEmpty()) {
            for (User user : users) {

                // 오늘 날짜
                LocalDate currentDate = LocalDate.now();

                LocalDateTime startTimeWithDate;
                LocalDateTime endTimeWithDate;

                // dailyEndTime 이 자정을 넘은 경우
                if (user.getDailyStartTime().isAfter(user.getDailyEndTime())) {
                    startTimeWithDate = LocalDateTime.of(currentDate, user.getDailyStartTime());
                    endTimeWithDate = LocalDateTime.of(currentDate.plusDays(1), user.getDailyEndTime());
                } else { // 자정을 넘지 않은 경우
                    startTimeWithDate = LocalDateTime.of(currentDate, user.getDailyStartTime());
                    endTimeWithDate = LocalDateTime.of(currentDate, user.getDailyEndTime());
                }

                List<LocalDateTime> randomPushNotificationTime = LocalDateTimeUtil.generateRandomDateTimes(
                        startTimeWithDate,
                        endTimeWithDate,
                        Duration.ofHours(2)
                );

                NotificationTime notificationTime = notificationTimeRetriever.findNotificationTimeByUser(user);
                notificationTimeUpdater.setUserDailyNotificationTime(notificationTime, randomPushNotificationTime);
            }
        }
    }
}
