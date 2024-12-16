package com.fourcut.diary.user.util;

import com.fourcut.diary.user.domain.notification.NotificationTime;
import com.fourcut.diary.user.service.dto.TakePhotoInfoDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotificationTimeUtil {

    private static final int EXPIRATION_MINUTES = 20;

    public static TakePhotoInfoDto getTakePhotoInfo(NotificationTime notificationTime, LocalDateTime now) {
        List<LocalDateTime> timeSlots = List.of(
                notificationTime.getFirstTimeSlot(),
                notificationTime.getSecondTimeSlot(),
                notificationTime.getThirdTimeSlot(),
                notificationTime.getFourthTimeSlot()
        );

        for (int i = 0; i < timeSlots.size(); i++) {
            LocalDateTime slot = timeSlots.get(i);
            if (getIsPossiblePhotoCapture(slot, now)) {
                return new TakePhotoInfoDto(i + 1, slot.plusMinutes(EXPIRATION_MINUTES).toLocalTime());
            }
        }
        return new TakePhotoInfoDto(-1, null);
    }

    public static Boolean getIsPossiblePhotoCapture(LocalDateTime beginningTime, LocalDateTime now) {
        LocalDateTime expirationTime = beginningTime.plusMinutes(20);
        return !now.isBefore(beginningTime) && !now.isAfter(expirationTime);
    }
}
