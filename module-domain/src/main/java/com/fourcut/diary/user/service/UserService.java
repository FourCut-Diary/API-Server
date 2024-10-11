package com.fourcut.diary.user.service;

import com.fourcut.diary.user.domain.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserCreator userCreator;
    private final UserRetriever userRetriever;

    @Transactional
    public Long createUser(String socialId, String nickname, LocalDate birthday, Gender gender, LocalTime dailyStartTime, LocalTime dailyEndTime, String snsArnEndpoint) {

        userRetriever.checkAlreadyExistedUser(socialId);
        return userCreator.createUser(socialId, nickname, birthday, gender, dailyStartTime, dailyEndTime, snsArnEndpoint);
    }

    @Transactional(readOnly = true)
    public Long getUserId(String socialId) {
        return userRetriever.getUserBySocialId(socialId).getId();
    }
}
