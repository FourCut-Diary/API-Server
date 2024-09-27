package com.fourcut.diary.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LocalDateTimeUtil {

    public static List<LocalDateTime> generateRandomDateTimes(LocalDateTime start, LocalDateTime end, Duration minInterval) {
        List<LocalDateTime> dateTimes = new ArrayList<>();

        Random random = new Random();

        // 주어진 간격 안에 총 4번의 랜덤한 시간을 생성 (시간은 최소 minInterval 만큼의 간격)
        while (dateTimes.size() < 4) {
            long secondsBetween = Duration.between(start, end).getSeconds();
            long randomSeconds = random.nextInt((int) secondsBetween);

            LocalDateTime randomDateTime = start.plusDays(1).plusSeconds(randomSeconds);

            // 최소 간격을 만족하는지 확인
            if (isValidDateTime(randomDateTime, dateTimes, minInterval)) {
                dateTimes.add(randomDateTime);
            }
        }

        Collections.sort(dateTimes);
        return dateTimes;
    }

    private static boolean isValidDateTime(LocalDateTime newDateTime, List<LocalDateTime> existingDateTimes, Duration minInterval) {
        for (LocalDateTime dateTime : existingDateTimes) {
            if (Duration.between(dateTime, newDateTime).abs().compareTo(minInterval) < 0) {
                return false;
            }
        }
        return true;
    }
}
