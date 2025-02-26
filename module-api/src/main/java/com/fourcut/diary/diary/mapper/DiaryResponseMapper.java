package com.fourcut.diary.diary.mapper;

import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.dto.response.DiaryDetailResponse;
import com.fourcut.diary.diary.dto.response.MonthDiaryResponse;
import com.fourcut.diary.diary.dto.response.TodayDiaryResponse;
import com.fourcut.diary.diary.service.dto.MonthDiaryDto;
import org.springframework.stereotype.Component;

@Component
public class DiaryResponseMapper {

    public TodayDiaryResponse toTodayDiaryResponse(Diary diary) {
        return new TodayDiaryResponse(
                diary.getId(),
                diary.getDate(),
                diary.getFirstPicture(),
                diary.getSecondPicture(),
                diary.getThirdPicture(),
                diary.getFourthPicture(),
                diary.getFirstComment(),
                diary.getSecondComment(),
                diary.getThirdComment(),
                diary.getFourthComment()
        );
    }

    public DiaryDetailResponse toDiaryDetailResponse(Diary diary) {
        return new DiaryDetailResponse(
                diary.getDate(),
                diary.getImageUrl()
        );
    }

    public MonthDiaryResponse toMonthDiaryResponse(MonthDiaryDto dto) {
        return new MonthDiaryResponse(dto.month(), dto.diaryList(), dto.recordCount());
    }
}
