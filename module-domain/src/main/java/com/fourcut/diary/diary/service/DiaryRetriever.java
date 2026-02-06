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

    /**
     * 완료된 일기 개수 반환 (제목과 최종 이미지가 모두 있는 경우만 카운트)
     */
    public Integer countCompletedDiaryByUser(User user) {
        return diaryRepository.countByUserAndTitleIsNotNullAndImageUrlIsNotNull(user);
    }

    public boolean existsDiary(User user, LocalDate date) {
        return diaryRepository.existsByUserAndDate(user, date);
    }
}
