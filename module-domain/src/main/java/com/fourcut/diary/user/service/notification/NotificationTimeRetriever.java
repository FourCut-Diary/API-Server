package com.fourcut.diary.user.service.notification;

import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.exception.model.NotFoundException;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.domain.notification.NotificationTime;
import com.fourcut.diary.user.repository.notification.NotificationTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationTimeRetriever {

    private final NotificationTimeRepository notificationTimeRepository;

    public List<String> findAllUserArnEndpointByCurrentTime(LocalDateTime currentTime) {

        currentTime = currentTime.withSecond(0).withNano(0);
        return notificationTimeRepository.findAllUserArnEndpointByCurrentTime(currentTime);
    }

    public NotificationTime findNotificationTimeByUser(User user) {

        return notificationTimeRepository.findNotificationTimeByUser(user)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND_NOTIFICATION_TIME));
    }
}
