package com.fourcut.diary.user.service;

import com.fourcut.diary.user.domain.Gender;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreator {

    private final UserRepository userRepository;

    public Long createUser(String socialId, String name, String nickname, LocalDate birthday, Gender gender) {
        User newUser = User.newInstance(socialId, name, nickname, birthday, gender);
        userRepository.save(newUser);

        return newUser.getId();
    }
}
