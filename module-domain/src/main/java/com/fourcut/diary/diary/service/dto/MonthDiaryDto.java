package com.fourcut.diary.diary.service.dto;

import com.fourcut.diary.diary.repository.dto.DiaryImageDto;

import java.time.LocalDate;
import java.util.List;

public record MonthDiaryDto(

        MonthDiaryList monthDiaryList,

        Integer recordCount
) {

    public record MonthDiaryList(

            LocalDate month,

            List<DiaryImageDto> diaryList
    ) {}
}
