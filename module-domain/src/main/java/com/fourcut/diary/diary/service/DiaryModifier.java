package com.fourcut.diary.diary.service;

import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryModifier {

    private final DiaryRepository diaryRepository;

    public void enrollPhotoInDiary(Diary diary, String imageUrl, Integer index) {
        diaryRepository.enrollPictureInDiary(diary.getId(), imageUrl, index);
    }
}

