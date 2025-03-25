package com.fourcut.diary.notification.service;

import com.fourcut.diary.notification.domain.Notification;
import com.fourcut.diary.notification.repository.NotificationRepository;
import com.fourcut.diary.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationModifier {

    private final NotificationRepository notificationRepository;

    public void createNotification(User user, List<LocalDateTime> timeSlot) {
        notificationRepository.save(Notification.builder()
                .firstTimeSlot(timeSlot.get(0))
                .secondTimeSlot(timeSlot.get(1))
                .thirdTimeSlot(timeSlot.get(2))
                .fourthTimeSlot(timeSlot.get(3))
                .user(user)
                .build()
        );
    }

    public void updateNextDayNotificationTime(Notification notification, List<LocalDateTime> timeSlots) {
        notification.updateTimeSlot(timeSlots);
    }
}
