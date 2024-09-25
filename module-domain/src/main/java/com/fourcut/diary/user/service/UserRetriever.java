package com.fourcut.diary.user.service;

import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.exception.model.ConflictException;
import com.fourcut.diary.exception.model.NotFoundException;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.repository.UserRepository;
import com.fourcut.diary.user.repository.UserRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserRetriever {

    private final UserRepository userRepository;
    private final UserRepositoryCustomImpl userRepositoryCustomImpl;

    public boolean isExistingUser(String socialId) {
        return userRepository.existsBySocialId(socialId);
    }

    public void checkAlreadyExistedUser(String socialId) {
        if (userRepository.existsBySocialId(socialId)) {
            throw new ConflictException(ErrorMessage.ALREADY_ENROLL_USER);
        }
    }

    public User getUserBySocialId(String socialId) {
        return userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND_USER));
    }

    public List<Long> getUserIdListWithExpiredDailyEndTime(LocalTime currentTime) {

        return userRepositoryCustomImpl.findAllUserIdsWithExpiredDailyEndTime(currentTime);
    }
}
