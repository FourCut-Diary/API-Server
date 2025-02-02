package com.fourcut.diary.diary.dto;

import com.fourcut.diary.diary.repository.dto.DiaryImageDto;

import java.time.LocalDate;
import java.util.List;

public record MonthDiaryResponse(

        LocalDate month,

        List<DiaryImageDto> diaryList,

        Integer recordCount
) {

}
