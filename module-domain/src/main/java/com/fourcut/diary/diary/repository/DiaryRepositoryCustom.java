package com.fourcut.diary.diary.repository;

import com.fourcut.diary.diary.repository.dto.DiaryImageDto;
import com.fourcut.diary.user.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepositoryCustom {

    List<DiaryImageDto> findDiaryImageByMonth(User user, LocalDate date);
}
