package com.fourcut.diary.diary.service;

import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.repository.dto.DiaryImageDto;
import com.fourcut.diary.diary.service.dto.MonthDiaryDto;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.service.UserRetriever;
import com.fourcut.diary.user.service.dto.PhotoCaptureInfoDto;
import com.fourcut.diary.util.LocalDateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private static final int EXPIRATION_MINUTES = 20;

    private final UserRetriever userRetriever;

    private final DiaryRetriever diaryRetriever;

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

    @Transactional
    public PhotoCaptureInfoDto getTakePhotoInfoByUser(String socialId) {
        User user = userRetriever.getUserBySocialId(socialId);
        Diary diary = diaryRetriever.getTodayDiary(user);
        LocalDateTime now = LocalDateTime.now();
        return getTakePhotoInfo(diary, now);
    }

    @Transactional(readOnly = true)
    public MonthDiaryDto getMonthDiaryByUser(String socialId, LocalDate date) {
        User user = userRetriever.getUserBySocialId(socialId);
        List<DiaryImageDto> diaryList = diaryRetriever.findDiaryImageByMonth(user, date);
        Integer countDiary = diaryRetriever.countDiaryByUser(user);

        return new MonthDiaryDto(date.withDayOfMonth(1), diaryList, countDiary);
    }

    private PhotoCaptureInfoDto getTakePhotoInfo(Diary diary, LocalDateTime now) {
        List<LocalDateTime> timeSlots = List.of(
                diary.getFirstTimeSlot(),
                diary.getSecondTimeSlot(),
                diary.getThirdTimeSlot(),
                diary.getFourthTimeSlot()
        );

        for (int i = 0; i < timeSlots.size(); i++) {
            LocalDateTime slot = timeSlots.get(i);
            if (LocalDateTimeUtil.getIsPossiblePhotoCapture(slot, now)) {
                return new PhotoCaptureInfoDto(i + 1, slot.plusMinutes(EXPIRATION_MINUTES).toLocalTime());
            }
        }
        return new PhotoCaptureInfoDto(-1, null);
    }
}
