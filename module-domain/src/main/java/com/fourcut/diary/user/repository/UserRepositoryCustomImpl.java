package com.fourcut.diary.user.repository;

import com.fourcut.diary.user.domain.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findAllUserIdsWithExpiredDailyEndTime(LocalTime currentTime) {

        QUser user = QUser.user;

        return queryFactory.select(user.id)
                .from(user)
                .where(user.dailyEndTime.eq(currentTime))
                .fetch();
    }
}
