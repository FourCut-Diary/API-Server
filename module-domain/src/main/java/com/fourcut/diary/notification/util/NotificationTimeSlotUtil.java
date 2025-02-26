package com.fourcut.diary.notification.util;

import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.util.LocalDateTimeUtil;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotificationTimeSlotUtil {

    public static List<LocalDateTime> getRandomTimeSlot(User user, LocalDate currentDate) {
        LocalDateTime startTime = calculateStartTime(user, currentDate);
        LocalDateTime endTime = calculateEndTime(user, currentDate);

        return LocalDateTimeUtil.generateRandomDateTimes(
                startTime, endTime, Duration.ofHours(2), false
        );
    }

    private static LocalDateTime calculateStartTime(User user, LocalDate currentDate) {
        return LocalDateTime.of(currentDate, user.getDailyStartTime());
    }

    private static LocalDateTime calculateEndTime(User user, LocalDate currentDate) {
        if (user.isOverDay()) {
            return LocalDateTime.of(currentDate.plusDays(1), user.getDailyEndTime());
        }
        return LocalDateTime.of(currentDate, user.getDailyEndTime());
    }
}
