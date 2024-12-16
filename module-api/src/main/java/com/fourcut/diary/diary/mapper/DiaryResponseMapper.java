package com.fourcut.diary.diary.mapper;

import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.dto.TodayDiaryResponse;
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
}
