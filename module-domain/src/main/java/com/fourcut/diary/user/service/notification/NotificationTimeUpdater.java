package com.fourcut.diary.user.service.notification;

import com.fourcut.diary.user.domain.notification.NotificationTime;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotificationTimeUpdater {

    public void setUserDailyNotificationTime(NotificationTime notificationTime, List<LocalDateTime> randomTimes) {

        if (randomTimes == null || randomTimes.size() != 4) {
            throw new InvalidParameterException("잘못된 인자가 전달됐습니다.");
        }

        notificationTime.updateNotificationTime(randomTimes);
    }
}
