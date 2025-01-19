package com.fourcut.diary.diary.service;

import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.diary.repository.DiaryRepository;
import com.fourcut.diary.diary.util.DiaryTimeSlotUtil;
import com.fourcut.diary.exception.model.NotFoundException;
import com.fourcut.diary.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DiaryRetriever {

    private final DiaryRepository diaryRepository;

    public Diary getTodayDiary(User user) {
        LocalDate today = LocalDate.now();
        List<LocalDateTime> timeSlots = DiaryTimeSlotUtil.getRandomTimeSlot(user, today);
        return diaryRepository.findByUserAndDate(user, today)
                .orElseGet(() -> diaryRepository.save(
                        Diary.builder()
                                .date(today)
                                .user(user)
                                .firstTimeSlot(timeSlots.get(0))
                                .secondTimeSlot(timeSlots.get(1))
                                .thirdTimeSlot(timeSlots.get(2))
                                .fourthTimeSlot(timeSlots.get(3))
                                .build()
                ));
    }

    public Diary getDiary(User user, LocalDate date) {
        return diaryRepository.findByUserAndDate(user, date)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND_DIARY));
    }
}
