package com.fourcut.diary.diary.repository;

import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.repository.dto.DiaryImageDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DiaryRepositoryCustom {

    List<DiaryImageDto> findDiaryImageByMonth(Long userId, LocalDate date);
    List<Diary> findExpiredTodayDiary(LocalDateTime now);

    void enrollPictureInDiary(Long diaryId, String imageUrl, Integer index, String comment);
}
