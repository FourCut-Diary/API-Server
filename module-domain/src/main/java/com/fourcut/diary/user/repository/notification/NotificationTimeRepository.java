package com.fourcut.diary.user.repository.notification;

import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.domain.notification.NotificationTime;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NotificationTimeRepository extends CrudRepository<NotificationTime, Long>, NotificationTimeRepositoryCustom {

    // CREATE

    // READ
    Optional<NotificationTime> findNotificationTimeByUser(User user);

    // UPDATE

    // DELETE
}
