package com.fourcut.diary.notification.repository;

import com.fourcut.diary.notification.domain.Notification;
import com.fourcut.diary.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository  extends JpaRepository<Notification, Long> {

    Optional<Notification> findByUser(User user);

    @Query("SELECT DISTINCT n FROM Notification n JOIN FETCH n.user")
    List<Notification> findAllWithUser();
}
