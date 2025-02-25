package com.fourcut.diary.diary.util;

import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.util.LocalDateTimeUtil;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiaryTimeSlotUtil {

    public static List<LocalDateTime> getRandomTimeSlot(User user, LocalDate currentDate, boolean isFinishedToday) {
        LocalDateTime startTime = calculateStartTime(user, currentDate);
        LocalDateTime endTime = calculateEndTime(user, currentDate);

        List<LocalDateTime> randomTimeSlots = LocalDateTimeUtil.generateRandomDateTimes(
                startTime, endTime, Duration.ofHours(2), false
        );

        if (isFinishedToday) {
            return randomTimeSlots.stream()
                    .map(timeSlot -> timeSlot.plusDays(1))
                    .collect(Collectors.toList());
        }

        return randomTimeSlots;
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
