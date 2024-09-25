package com.fourcut.diary.user.service;

import com.fourcut.diary.user.domain.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserCreator userCreator;
    private final UserRetriever userRetriever;

    public Long createUser(String socialId, String nickname, LocalDate birthday, Gender gender, LocalTime dailyStartTime, LocalTime dailyEndTime) {

        userRetriever.checkAlreadyExistedUser(socialId);
        return userCreator.createUser(socialId, nickname, birthday, gender, dailyStartTime, dailyEndTime);
    }

    public Long getUser(String socialId) {
        return userRetriever.getUserBySocialId(socialId).getId();
    }
}
