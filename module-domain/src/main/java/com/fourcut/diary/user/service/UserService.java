package com.fourcut.diary.user.service;

import com.fourcut.diary.user.domain.Gender;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.service.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

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
    public User getUserBySocialId(String socialId) {

        return userRetriever.getUserBySocialId(socialId);
    }

    @Transactional(readOnly = true)
    public UserProfileDto getUserProfileInfoBySocialId(String socialId) {

        User user = userRetriever.getUserBySocialId(socialId);
        long daysAfterRegistration = ChronoUnit.DAYS.between(user.getCreatedAt(), LocalDateTime.now()) + 1;
        return new UserProfileDto(user.getId(), "image", user.getNickname(), daysAfterRegistration);
    }
}
