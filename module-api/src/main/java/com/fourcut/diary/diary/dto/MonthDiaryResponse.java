package com.fourcut.diary.diary.dto;

import com.fourcut.diary.diary.service.dto.MonthDiaryDto.MonthDiaryList;

public record MonthDiaryResponse(

        MonthDiaryList monthList,

        Integer recordCount
) {

}
