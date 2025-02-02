package com.fourcut.diary.diary.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record MonthDiaryRequest(

        @NotNull
        List<LocalDate> monthList
) {
}
