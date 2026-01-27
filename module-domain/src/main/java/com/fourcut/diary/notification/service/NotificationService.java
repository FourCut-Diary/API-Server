package com.fourcut.diary.notification.service;

import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.service.DiaryRetriever;
import com.fourcut.diary.notification.domain.Notification;
import com.fourcut.diary.notification.util.NotificationTimeSlotUtil;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.service.UserRetriever;
import com.fourcut.diary.user.service.dto.PictureCaptureInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final int EXPIRATION_MINUTES = 20;

    private final UserRetriever userRetriever;
    private final DiaryRetriever diaryRetriever;

    private final NotificationRetriever notificationRetriever;
    private final NotificationModifier notificationModifier;

    @Transactional
    public void createNotification(String socialId) {
        User newUser = userRetriever.getUserBySocialId(socialId);
        List<LocalDateTime> timeSlot = NotificationTimeSlotUtil.getRandomTimeSlot(newUser, LocalDate.now());
        notificationModifier.createNotification(newUser, timeSlot);
    }

    @Transactional
    public PictureCaptureInfoDto getTakePictureInfoByUser(String socialId) {
        User user = userRetriever.getUserBySocialId(socialId);
        Notification notification = notificationRetriever.getTodayNotification(user);
        Diary todayDiary = diaryRetriever.getTodayDiary(user);
        LocalDateTime now = LocalDateTime.now();
        return getTakePictureInfo(notification, todayDiary, now);
    }

    @Transactional
    public void updateNotificationTime(LocalDate nextDay) {
        List<Notification> notifications = notificationRetriever.getAllNotificationsWithUser();
        notifications.forEach(notification -> {
            List<LocalDateTime> timeSlots = NotificationTimeSlotUtil.getRandomTimeSlot(notification.getUser(), nextDay);
            notificationModifier.updateNextDayNotificationTime(notification, timeSlots);
        });
    }

    @Transactional(readOnly = true)
    public List<Notification> getAllNotifications() {
        return notificationRetriever.getAllNotifications();
    }

    private PictureCaptureInfoDto getTakePictureInfo(Notification notification, Diary todayDiary, LocalDateTime now) {
        List<LocalDateTime> timeSlots = List.of(
                notification.getFirstTimeSlot(),
                notification.getSecondTimeSlot(),
                notification.getThirdTimeSlot(),
                notification.getFourthTimeSlot()
        );

        for (int i = 0; i < timeSlots.size(); i++) {
            LocalDateTime slot = timeSlots.get(i);
            int pictureIndex = i + 1;
            // 시간 슬롯 내에 있고, 해당 슬롯에 아직 사진이 등록되지 않은 경우에만 촬영 가능
            if (notification.isPossibleEnrollPicture(now, pictureIndex) && !todayDiary.hasPictureAt(pictureIndex)) {
                return new PictureCaptureInfoDto(pictureIndex, slot.plusMinutes(EXPIRATION_MINUTES).toLocalTime());
            }
        }
        return new PictureCaptureInfoDto(-1, null);
    }
}
