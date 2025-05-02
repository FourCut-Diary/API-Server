package com.fourcut.diary.service;

import com.fourcut.diary.diary.service.DiaryService;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class SchedulerServiceQueryCountTest {

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private DiaryService diaryServiceOptimized; // 최적화된 버전 메서드 포함

    private final LocalDate nextDay = LocalDate.now().plusDays(1);

    @BeforeEach
    void enableStatistics() {
        SessionFactoryImplementor sessionFactory =
                emf.unwrap(SessionFactoryImplementor.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
    }

    @Test
    void countQueriesBeforeAndAfter() {
        SessionFactoryImplementor sessionFactory =
                emf.unwrap(SessionFactoryImplementor.class);
        Statistics stats = sessionFactory.getStatistics();

        // 기존 버전 실행
        stats.clear();
        diaryService.createNextDayDiaries(nextDay);
        long before = stats.getPrepareStatementCount();
        System.out.println("기존 쿼리 수: " + before);

        // 최적화 버전 실행
        stats.clear();
        diaryServiceOptimized.createNextDayDiaries(nextDay);
        long after = stats.getPrepareStatementCount();
        System.out.println("최적화된 쿼리 수: " + after);

        assertTrue(after < before, "최적화된 버전이 더 적은 쿼리를 실행해야 합니다.");
    }
}
