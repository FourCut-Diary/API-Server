package com.fourcut.diary.diary.service;

import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryModifier {

    private final DiaryRepository diaryRepository;

    public void enrollPictureInDiary(Diary diary, String imageUrl, Integer index, String comment) {
        diaryRepository.enrollPictureInDiary(diary.getId(), imageUrl, index, comment);
    }

    public void enrollDailyDiary(Diary diary, String imageUrl, String title) {
        diary.enrollTitle(title);
        diary.enrollDailyPicture(imageUrl);
    }
}

