package com.fourcut.diary.user.repository;

import java.time.LocalTime;
import java.util.List;

public interface UserRepositoryCustom {

    // CREATE

    // READ
    List<Long> findAllUserIdsWithExpiredDailyEndTime(LocalTime currentTime);

    // UPDATE

    // DELETE
}
