package com.fourcut.diary.diary.service;

import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.repository.DiaryRepository;
import com.fourcut.diary.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DiaryRetriever {

    private final DiaryRepository diaryRepository;

    public Diary getTodayDiary(User user) {
        LocalDate today = LocalDate.now();
        return diaryRepository.findByUserAndDate(user, today)
                .orElseGet(() -> diaryRepository.save(
                        Diary.builder()
                                .date(today)
                                .user(user)
                                .build()
                ));
    }
}
