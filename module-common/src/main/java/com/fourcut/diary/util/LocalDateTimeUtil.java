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
            long minutesBetween = Duration.between(start, end).toMinutes();
            long randomMinutes = random.nextInt((int) minutesBetween + 1);

            // 초는 항상 0으로 설정
            LocalDateTime randomDateTime = start.plusDays(1).plusMinutes(randomMinutes).withSecond(0).withNano(0);

            // 최소 간격을 만족하는지 확인
            if (isValidDateTime(randomDateTime, dateTimes, minInterval)) {
                dateTimes.add(randomDateTime);
            }
        }

        Collections.sort(dateTimes);
        return dateTimes;
    }

    public static Boolean getIsPossiblePhotoCapture(LocalDateTime beginningTime, LocalDateTime now) {
        LocalDateTime expirationTime = beginningTime.plusMinutes(20);
        return !now.isBefore(beginningTime) && !now.isAfter(expirationTime);
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