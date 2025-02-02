package com.fourcut.diary.diary.service.dto;

import com.fourcut.diary.diary.repository.dto.DiaryImageDto;

import java.time.LocalDate;
import java.util.List;

public record MonthDiaryDto(

        LocalDate month,

        List<DiaryImageDto> diaryList,

        Integer recordCount
) {
}
