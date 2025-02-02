package com.fourcut.diary.user.service;

import com.fourcut.diary.user.domain.Gender;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserModifier {

    private final UserRepository userRepository;

    public Long createUser(String socialId, String name, LocalDate birthday, Gender gender, LocalTime dailyStartTime, LocalTime dailyEndTime, String snsArnEndpoint) {
        User newUser = User.newInstance(socialId, name, birthday, gender, dailyStartTime, dailyEndTime, snsArnEndpoint);
        userRepository.save(newUser);

        return newUser.getId();
    }
}
