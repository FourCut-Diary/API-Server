package com.fourcut.diary.diary.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MonthDiaryRequest(

        @NotNull
        List<Integer> monthList
) {
}
