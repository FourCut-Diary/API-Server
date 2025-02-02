package com.fourcut.diary.diary.repository;

import com.fourcut.diary.diary.domain.QDiary;
import com.fourcut.diary.diary.repository.dto.DiaryImageDto;
import com.fourcut.diary.user.domain.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class DiaryRepositoryCustomImpl implements DiaryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DiaryImageDto> findDiaryImageByMonth(User user, LocalDate date) {

        QDiary diary = QDiary.diary;

        LocalDate startOfMonth = date.withDayOfMonth(1);
        LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());

        return queryFactory
                .select(
                        Projections.constructor(
                                DiaryImageDto.class,
                                diary.id,
                                diary.imageUrl,
                                diary.date
                        )
                )
                .from(diary)
                .where(diary.date.between(startOfMonth, endOfMonth))
                .fetch();
    }
}
