package com.fourcut.diary.user.repository;

import com.fourcut.diary.user.domain.User;

import java.time.LocalTime;
import java.util.List;

public interface UserRepositoryCustom {

    List<User> findAllUserWithExpiredDailyEndTime(LocalTime currentTime);
}
