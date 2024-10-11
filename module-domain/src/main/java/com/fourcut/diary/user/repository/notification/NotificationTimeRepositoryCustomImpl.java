package com.fourcut.diary.user.repository.notification;

import com.fourcut.diary.user.domain.notification.QNotificationTime;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class NotificationTimeRepositoryCustomImpl implements NotificationTimeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findAllUserArnEndpointByCurrentTime(LocalDateTime currentTime) {

        QNotificationTime notificationTime = QNotificationTime.notificationTime;

        return queryFactory
                .select(notificationTime.user.snsArnEndpoint)
                .from(notificationTime)
                .where(notificationTime.firstTimeSlot.eq(currentTime)
                        .or(notificationTime.secondTimeSlot.eq(currentTime))
                        .or(notificationTime.thirdTimeSlot.eq(currentTime))
                        .or(notificationTime.fourthTimeSlot.eq(currentTime))
                )
                .fetch();
    }
}
