package com.fourcut.diary.notification.service;

import com.fourcut.diary.notification.domain.Notification;
import com.fourcut.diary.notification.repository.NotificationRepository;
import com.fourcut.diary.notification.util.NotificationTimeSlotUtil;
import com.fourcut.diary.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationRetriever {

    private final NotificationRepository notificationRepository;

    public Notification getTodayNotification(User user) {
        return notificationRepository.findByUser(user)
                .orElseGet(() -> {
                    LocalDate today = LocalDate.now();
                    List<LocalDateTime> timeSlots = NotificationTimeSlotUtil.getRandomTimeSlot(user, today);
                    return notificationRepository.save(
                            Notification.builder()
                                    .firstTimeSlot(timeSlots.get(0))
                                    .secondTimeSlot(timeSlots.get(1))
                                    .thirdTimeSlot(timeSlots.get(2))
                                    .fourthTimeSlot(timeSlots.get(3))
                                    .user(user)
                                    .build()
                    );
                });
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getAllNotificationsWithUser() {
        return notificationRepository.findAllWithUser();
    }
}
