package com.fourcut.diary.diary.repository;

import com.fourcut.diary.diary.domain.QDiary;
import com.fourcut.diary.diary.repository.dto.DiaryDetailInfoDto;
import com.fourcut.diary.user.domain.notification.QNotificationTime;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class DiaryRepositoryCustomImpl implements DiaryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public DiaryDetailInfoDto findDiaryDetailInfo(Long userId, LocalDate date) {

        QDiary diary = QDiary.diary;
        QNotificationTime notificationTime = QNotificationTime.notificationTime;

        return queryFactory
                .select(Projections.constructor(
                    DiaryDetailInfoDto.class,
                    diary.date,
                    diary.firstPicture,
                    diary.secondPicture,
                    diary.thirdPicture,
                    diary.fourthPicture,
                    diary.firstComment,
                    diary.secondComment,
                    diary.thirdComment,
                    diary.fourthComment,
                    notificationTime.firstTimeSlot,
                    notificationTime.secondTimeSlot,
                    notificationTime.thirdTimeSlot,
                    notificationTime.fourthTimeSlot
                ))
                .from(diary)
                .innerJoin(notificationTime)
                .on(diary.user.id.eq(userId))
                .where(diary.date.eq(date))
                .fetchOne();
    }
}
