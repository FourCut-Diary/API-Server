package com.fourcut.diary.user.service;

import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.exception.model.ConflictException;
import com.fourcut.diary.exception.model.NotFoundException;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserRetriever {

    private final UserRepository userRepository;

    public void checkAlreadyExistedUser(String socialId) {
        if (userRepository.existsBySocialId(socialId)) {
            throw new ConflictException(ErrorMessage.ALREADY_ENROLL_USER);
        }
    }

    public User getUserBySocialId(String socialId) {
        return userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND_USER));
    }

    public void checkExistUser(String socialId) {
        if (!userRepository.existsBySocialId(socialId)) {
            throw new NotFoundException(ErrorMessage.NOT_FOUND_USER);
        }
    }

    public List<User> getAllUsersWithoutDiary(LocalDate nextDay) {
        return userRepository.findAllWithoutDiaryOn(nextDay);
    }
}
