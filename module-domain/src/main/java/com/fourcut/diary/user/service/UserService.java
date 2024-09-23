package com.fourcut.diary.user.service;

import com.fourcut.diary.user.domain.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserCreator userCreator;
    private final UserRetriever userRetriever;

    public Long createUser(String socialId, String name, String nickname, LocalDate birthday, Gender gender) {

        userRetriever.checkAlreadyExistedUser(socialId);
        return userCreator.createUser(socialId, name, nickname, birthday, gender);
    }

    public Long getUser(String socialId) {
        return userRetriever.getUserBySocialId(socialId).getId();
    }
}
