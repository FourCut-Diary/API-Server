package com.fourcut.diary.diary.dto;

import java.time.LocalDate;
import java.util.List;

public record MonthDiaryResponse(

        List<MonthDiaryResponseItem> diaryList,

        Integer recordCount
) {

    public record MonthDiaryResponseItem(

            Long diaryId,

            String diaryImage,

            LocalDate date
    ) {}
}
