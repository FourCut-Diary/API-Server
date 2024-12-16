package com.fourcut.diary.user.service.notification;

import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.domain.notification.NotificationTime;
import com.fourcut.diary.user.service.dto.TakePhotoInfoDto;
import com.fourcut.diary.util.LocalDateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationTimeService {

    private static final int EXPIRATION_MINUTES = 20;

    private final NotificationTimeRetriever notificationTimeRetriever;

    @Transactional(readOnly = true)
    public TakePhotoInfoDto getTakePhotoInfoByUser(User user) {
        NotificationTime notificationTime = notificationTimeRetriever.findNotificationTimeByUser(user);
        LocalDateTime now = LocalDateTime.now();
        return getTakePhotoInfo(notificationTime, now);
    }

    private TakePhotoInfoDto getTakePhotoInfo(NotificationTime notificationTime, LocalDateTime now) {
        List<LocalDateTime> timeSlots = List.of(
                notificationTime.getFirstTimeSlot(),
                notificationTime.getSecondTimeSlot(),
                notificationTime.getThirdTimeSlot(),
                notificationTime.getFourthTimeSlot()
        );

        for (int i = 0; i < timeSlots.size(); i++) {
            LocalDateTime slot = timeSlots.get(i);
            if (LocalDateTimeUtil.getIsPossiblePhotoCapture(slot, now)) {
                return new TakePhotoInfoDto(i + 1, slot.plusMinutes(EXPIRATION_MINUTES).toLocalTime());
            }
        }
        return new TakePhotoInfoDto(-1, null);
    }
}
