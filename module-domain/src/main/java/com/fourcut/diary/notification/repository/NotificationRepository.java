package com.fourcut.diary.notification.repository;

import com.fourcut.diary.notification.domain.Notification;
import com.fourcut.diary.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository  extends JpaRepository<Notification, Long> {

    Optional<Notification> findByUser(User user);
}
