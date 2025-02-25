package com.fourcut.diary.diary.repository;

import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.domain.QDiary;
import com.fourcut.diary.diary.repository.dto.DiaryImageDto;
import com.fourcut.diary.exception.model.BadRequestException;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class DiaryRepositoryCustomImpl implements DiaryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DiaryImageDto> findDiaryImageByMonth(Long userId, LocalDate date) {

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
                .where(
                        diary.date.between(startOfMonth, endOfMonth),
                        diary.user.id.eq(userId)
                )
                .fetch();
    }

    @Override
    public List<Diary> findExpiredTodayDiary(LocalDateTime now) {

        QDiary diary = QDiary.diary;

        return queryFactory
                .selectFrom(diary)
                .where(isExpiredDiary(diary,now.minusMinutes(20)))
                .fetch();
    }

    @Override
    public void enrollPictureInDiary(Long diaryId, String imageUrl, Integer index, String comment) {

        QDiary diary = QDiary.diary;
        PathBuilder<Diary> entityPath = new PathBuilder<>(Diary.class, "diary");
        Path<String> pictureColumnPath = getPictureOrderColumnPath(entityPath, index);
        Path<String> commentColumnPath = getCommentOrderColumnPath(entityPath, index);

        queryFactory
                .update(diary)
                .set(pictureColumnPath, imageUrl)
                .set(commentColumnPath, comment)
                .where(diary.id.eq(diaryId))
                .execute();
    }

    private Path<String> getPictureOrderColumnPath(PathBuilder<Diary> entityPath, Integer columnIndex) {
        return switch (columnIndex) {
            case 1 -> entityPath.getString("firstPicture");
            case 2 -> entityPath.getString("secondPicture");
            case 3 -> entityPath.getString("thirdPicture");
            case 4 -> entityPath.getString("fourthPicture");
            default -> throw new BadRequestException(ErrorMessage.INVALID_PICTURE_INDEX);
        };
    }

    private Path<String> getCommentOrderColumnPath(PathBuilder<Diary> entityPath, Integer columnIndex) {
        return switch (columnIndex) {
            case 1 -> entityPath.getString("firstComment");
            case 2 -> entityPath.getString("secondComment");
            case 3 -> entityPath.getString("thirdComment");
            case 4 -> entityPath.getString("fourthComment");
            default -> throw new BadRequestException(ErrorMessage.INVALID_PICTURE_INDEX);
        };
    }

    private BooleanExpression isExpiredDiary(QDiary diary, LocalDateTime targetTime) {
        return diary.fourthTimeSlot.year().eq(targetTime.getYear())
                .and(diary.fourthTimeSlot.month().eq(targetTime.getMonthValue()))
                .and(diary.fourthTimeSlot.dayOfMonth().eq(targetTime.getDayOfMonth()))
                .and(diary.fourthTimeSlot.hour().eq(targetTime.getHour()))
                .and(diary.fourthTimeSlot.minute().eq(targetTime.getMinute()));
    }
}
