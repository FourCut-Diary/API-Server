package com.fourcut.diary.diary.mapper;

import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.dto.DiaryDetailResponse;
import com.fourcut.diary.diary.dto.MonthDiaryResponse;
import com.fourcut.diary.diary.dto.PhotoCaptureInfoResponse;
import com.fourcut.diary.diary.dto.TodayDiaryResponse;
import com.fourcut.diary.diary.service.dto.MonthDiaryDto;
import com.fourcut.diary.user.service.dto.PhotoCaptureInfoDto;
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

    public PhotoCaptureInfoResponse toPhotoCaptureInfoResponse(PhotoCaptureInfoDto dto) {
        boolean isCapturePossible = dto.currentPhotoIndex() != -1;
        return new PhotoCaptureInfoResponse(
                isCapturePossible,
                isCapturePossible ? dto.currentPhotoIndex() : null,
                isCapturePossible ? dto.photoCaptureExpiration() : null
        );
    }

    public MonthDiaryResponse toMonthDiaryResponse(MonthDiaryDto dto) {
        return new MonthDiaryResponse(dto.month(), dto.diaryList(), dto.recordCount());
    }
}
