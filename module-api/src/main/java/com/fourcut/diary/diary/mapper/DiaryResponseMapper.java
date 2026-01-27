package com.fourcut.diary.diary.mapper;

import com.fourcut.diary.aws.S3Service;
import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.dto.response.DiaryDetailResponse;
import com.fourcut.diary.diary.dto.response.MonthDiaryResponse;
import com.fourcut.diary.diary.dto.response.TodayDiaryResponse;
import com.fourcut.diary.diary.repository.dto.DiaryImageDto;
import com.fourcut.diary.diary.service.dto.MonthDiaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DiaryResponseMapper {

    private final S3Service s3Service;

    public TodayDiaryResponse toTodayDiaryResponse(Diary diary) {
        // null이 아닌 이미지 URL들만 수집
        List<String> nonEmptyUrls = new ArrayList<>();
        if (diary.getFirstPicture() != null) nonEmptyUrls.add(diary.getFirstPicture());
        if (diary.getSecondPicture() != null) nonEmptyUrls.add(diary.getSecondPicture());
        if (diary.getThirdPicture() != null) nonEmptyUrls.add(diary.getThirdPicture());
        if (diary.getFourthPicture() != null) nonEmptyUrls.add(diary.getFourthPicture());

        // presigned URL 생성
        Map<String, String> urlMapping = new HashMap<>();
        if (!nonEmptyUrls.isEmpty()) {
            List<String> presignedUrls = s3Service.createGetPresignedUrl(nonEmptyUrls);
            for (int i = 0; i < nonEmptyUrls.size(); i++) {
                urlMapping.put(nonEmptyUrls.get(i), presignedUrls.get(i));
            }
        }

        return new TodayDiaryResponse(
                diary.getId(),
                diary.getDate(),
                getPresignedUrl(diary.getFirstPicture(), urlMapping),
                getPresignedUrl(diary.getSecondPicture(), urlMapping),
                getPresignedUrl(diary.getThirdPicture(), urlMapping),
                getPresignedUrl(diary.getFourthPicture(), urlMapping),
                diary.getFirstComment(),
                diary.getSecondComment(),
                diary.getThirdComment(),
                diary.getFourthComment(),
                diary.getFirstCaptureTime(),
                diary.getSecondCaptureTime(),
                diary.getThirdCaptureTime(),
                diary.getFourthCaptureTime()
        );
    }

    public DiaryDetailResponse toDiaryDetailResponse(Diary diary) {
        String presignedUrl = diary.getImageUrl() != null
                ? s3Service.createGetPresignedUrl(List.of(diary.getImageUrl())).get(0)
                : null;
        return new DiaryDetailResponse(
                diary.getDate(),
                presignedUrl
        );
    }

    public MonthDiaryResponse toMonthDiaryResponse(MonthDiaryDto dto) {
        List<String> allImageUrls = dto.diaryList().stream()
                .map(DiaryImageDto::imageUrl)
                .filter(url -> url != null && !url.isEmpty())
                .collect(Collectors.toList());

        Map<String, String> urlMapping = new HashMap<>();
        if (!allImageUrls.isEmpty()) {
            List<String> presignedUrls = s3Service.createGetPresignedUrl(allImageUrls);
            for (int i = 0; i < allImageUrls.size(); i++) {
                urlMapping.put(allImageUrls.get(i), presignedUrls.get(i));
            }
        }

        List<DiaryImageDto> diaryListWithPresignedUrl = dto.diaryList().stream()
                .map(diaryDto -> new DiaryImageDto(
                        diaryDto.id(),
                        getPresignedUrl(diaryDto.imageUrl(), urlMapping),
                        diaryDto.date()
                ))
                .collect(Collectors.toList());

        return new MonthDiaryResponse(dto.month(), diaryListWithPresignedUrl, dto.recordCount());
    }

    private String getPresignedUrl(String originalUrl, Map<String, String> urlMapping) {
        if (originalUrl == null || originalUrl.isEmpty()) {
            return null;
        }
        return urlMapping.get(originalUrl);
    }
}
