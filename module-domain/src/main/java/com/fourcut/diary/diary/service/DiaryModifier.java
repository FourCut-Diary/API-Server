package com.fourcut.diary.diary.service;

import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.repository.DiaryRepository;
import com.fourcut.diary.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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

    public void createDiary(LocalDate today, User user) {
        diaryRepository.save(Diary.builder()
                .date(today)
                .user(user)
                .build()
        );
    }
}

