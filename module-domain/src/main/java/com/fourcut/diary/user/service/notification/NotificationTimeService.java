package com.fourcut.diary.user.service.notification;

import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.domain.notification.NotificationTime;
import com.fourcut.diary.user.service.dto.TakePhotoInfoDto;
import com.fourcut.diary.user.util.NotificationTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationTimeService {

    private final NotificationTimeRetriever notificationTimeRetriever;

    @Transactional(readOnly = true)
    public TakePhotoInfoDto getTakePhotoInfoByUser(User user) {
        NotificationTime notificationTime = notificationTimeRetriever.findNotificationTimeByUser(user);
        LocalDateTime now = LocalDateTime.now();
        return NotificationTimeUtil.getTakePhotoInfo(notificationTime, now);
    }
}
