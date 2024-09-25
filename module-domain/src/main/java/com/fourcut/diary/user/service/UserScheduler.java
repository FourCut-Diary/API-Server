package com.fourcut.diary.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserScheduler {

    private final UserRetriever userRetriever;

    /*
    사용자가 설정한 활동 시간이 종료되면 1분 후에 다음 날의 푸시알림 시간이 설정됩니다.
    푸시알림 재설정이 필요한 사용자의 id 리스트를 반환하는 스케줄러 함수힙니다.
     */
    @Scheduled(cron = "0 * * * * ?")
    public List<Long> getUserByDailyEndTime() {

        return userRetriever.getUserIdListWithExpiredDailyEndTime(LocalTime.now());
    }
}
