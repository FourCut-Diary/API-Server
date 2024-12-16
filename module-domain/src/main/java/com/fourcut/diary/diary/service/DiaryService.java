package com.fourcut.diary.diary.service;

import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.service.UserRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final UserRetriever userRetriever;

    private final DiaryRetriever diaryRetriever;

    @Transactional
    public Diary getTodayDiary(String socialId) {

        User user = userRetriever.getUserBySocialId(socialId);
        return diaryRetriever.getTodayDiary(user);
    }
}
