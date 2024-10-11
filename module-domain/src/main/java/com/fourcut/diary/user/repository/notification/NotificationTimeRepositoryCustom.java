package com.fourcut.diary.user.repository.notification;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTimeRepositoryCustom {

    List<String> findAllUserArnEndpointByCurrentTime(LocalDateTime currentTime);
}
