package com.fourcut.diary.notification.service;

import com.fourcut.diary.notification.domain.Notification;
import com.fourcut.diary.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationModifier {

    private final NotificationRepository notificationRepository;

    public void updateNextDayNotificationTime(Notification notification, List<LocalDateTime> timeSlots) {
        notification.updateTimeSlot(timeSlots);
    }
}
