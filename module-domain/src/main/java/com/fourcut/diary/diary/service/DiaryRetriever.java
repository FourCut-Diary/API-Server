package com.fourcut.diary.diary.service;

import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.repository.DiaryRepository;
import com.fourcut.diary.diary.repository.dto.DiaryImageDto;
import com.fourcut.diary.exception.model.NotFoundException;
import com.fourcut.diary.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

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

    public Diary getDiary(User user, LocalDate date) {
        return diaryRepository.findByUserAndDate(user, date)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND_DIARY));
    }

    public List<DiaryImageDto> findDiaryImageByMonth(User user, LocalDate date) {
        return diaryRepository.findDiaryImageByMonth(user.getId(), date);
    }

    public Integer countDiaryByUser(User user) {
        return diaryRepository.countByUser(user);
    }

    public boolean existsDiary(User user, LocalDate date) {
        return diaryRepository.existsByUserAndDate(user, date);
    }
}
