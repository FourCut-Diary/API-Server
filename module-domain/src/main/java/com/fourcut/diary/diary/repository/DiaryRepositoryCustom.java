package com.fourcut.diary.diary.repository;

import com.fourcut.diary.diary.repository.dto.DiaryImageDto;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepositoryCustom {

    List<DiaryImageDto> findDiaryImageByMonth(Long userId, LocalDate date);

    void enrollPictureInDiary(Long diaryId, String imageUrl, Integer index, String comment);
}
