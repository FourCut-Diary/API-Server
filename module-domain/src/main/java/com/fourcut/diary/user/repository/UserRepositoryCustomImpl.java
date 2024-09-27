package com.fourcut.diary.user.repository;

import com.fourcut.diary.user.domain.QUser;
import com.fourcut.diary.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> findAllUserWithExpiredDailyEndTime(LocalTime currentTime) {

        QUser user = QUser.user;

        return queryFactory
                .selectFrom(user)
                .where(user.dailyEndTime.hour().eq(currentTime.getHour())
                        .and(user.dailyEndTime.minute().eq(currentTime.getMinute())))
                .fetch();
    }
}
