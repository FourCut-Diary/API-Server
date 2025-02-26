package com.fourcut.diary.notification.service;

import com.fourcut.diary.notification.domain.Notification;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.service.UserRetriever;
import com.fourcut.diary.user.service.dto.PictureCaptureInfoDto;
import com.fourcut.diary.util.LocalDateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final int EXPIRATION_MINUTES = 20;

    private final UserRetriever userRetriever;

    private final NotificationRetriever notificationRetriever;

    @Transactional
    public PictureCaptureInfoDto getTakePictureInfoByUser(String socialId) {
        User user = userRetriever.getUserBySocialId(socialId);
        Notification notification = notificationRetriever.getTodayNotification(user);
        LocalDateTime now = LocalDateTime.now();
        return getTakePictureInfo(notification, now);
    }

    private PictureCaptureInfoDto getTakePictureInfo(Notification notification, LocalDateTime now) {
        List<LocalDateTime> timeSlots = List.of(
                notification.getFirstTimeSlot(),
                notification.getSecondTimeSlot(),
                notification.getThirdTimeSlot(),
                notification.getFourthTimeSlot()
        );

        for (int i = 0; i < timeSlots.size(); i++) {
            LocalDateTime slot = timeSlots.get(i);
            if (LocalDateTimeUtil.getIsPossiblePhotoCapture(slot, now)) {
                return new PictureCaptureInfoDto(i + 1, slot.plusMinutes(EXPIRATION_MINUTES).toLocalTime());
            }
        }
        return new PictureCaptureInfoDto(-1, null);
    }
}
