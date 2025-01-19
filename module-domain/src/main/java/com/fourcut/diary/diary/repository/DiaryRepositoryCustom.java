package com.fourcut.diary.diary.repository;

import com.fourcut.diary.diary.repository.dto.DiaryDetailInfoDto;

import java.time.LocalDate;

public interface DiaryRepositoryCustom {

    DiaryDetailInfoDto findDiaryDetailInfo(Long userId, LocalDate date);
}
