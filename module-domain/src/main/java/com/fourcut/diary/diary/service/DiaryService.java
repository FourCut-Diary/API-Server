package com.fourcut.diary.diary.service;

import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.repository.dto.DiaryImageDto;
import com.fourcut.diary.diary.service.dto.MonthDiaryDto;
import com.fourcut.diary.exception.model.BadRequestException;
import com.fourcut.diary.notification.domain.Notification;
import com.fourcut.diary.notification.service.NotificationRetriever;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.service.UserRetriever;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {

    private static final int EXPIRATION_MINUTES = 20;

    private final UserRetriever userRetriever;

    private final DiaryRetriever diaryRetriever;
    private final DiaryModifier diaryModifier;

    private final NotificationRetriever notificationRetriever;

    @Transactional
    public Diary getTodayDiary(String socialId) {

        User user = userRetriever.getUserBySocialId(socialId);
        return diaryRetriever.getTodayDiary(user);
    }

    @Transactional(readOnly = true)
    public Diary getDiary(String socialId, LocalDate date) {

        User user = userRetriever.getUserBySocialId(socialId);
        return diaryRetriever.getDiary(user, date);
    }

    @Transactional(readOnly = true)
    public MonthDiaryDto getMonthDiaryByUser(String socialId, LocalDate date) {
        User user = userRetriever.getUserBySocialId(socialId);
        List<DiaryImageDto> diaryList = diaryRetriever.findDiaryImageByMonth(user, date);
        Integer countDiary = diaryRetriever.countDiaryByUser(user);

        return new MonthDiaryDto(date.withDayOfMonth(1), diaryList, countDiary);
    }

    @Transactional
    public void enrollPictureInDiary(String socialId, LocalDateTime now, String imageUrl, Integer index, String comment) {
        User user = userRetriever.getUserBySocialId(socialId);
        Diary diary = diaryRetriever.getTodayDiary(user);
        Notification notification = notificationRetriever.getTodayNotification(user);
        if(!notification.isPossibleEnrollPicture(now, index)) {
            throw new BadRequestException(ErrorMessage.INVALID_PICTURE_TIME);
        }
        diaryModifier.enrollPictureInDiary(diary, imageUrl, index, comment);
    }

    @Transactional
    public void enrollDiary(String socialId, String imageUrl, String title) {
        User user = userRetriever.getUserBySocialId(socialId);
        Diary diary = diaryRetriever.getTodayDiary(user);
        Notification notification = notificationRetriever.getTodayNotification(user);
        if (!notification.isFinished() && diary.getFourthPicture() == null) {
            throw new BadRequestException(ErrorMessage.TODAY_NOT_FINISH);
        }

        diaryModifier.enrollDailyDiary(diary, imageUrl, title);
    }

    @Transactional
    public void createNextDayDiaries(LocalDate nextDay) {
        List<User> allUser = userRetriever.getAllUsersWithoutDiary(nextDay);
        for (User u : allUser) {
            diaryModifier.createDiary(nextDay, u);
        }
    }
}
