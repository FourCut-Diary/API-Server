package com.fourcut.diary.service;

import com.fourcut.diary.diary.service.DiaryService;
import com.fourcut.diary.notification.service.NotificationService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
public class SchedulerServiceQueryCountTest {

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private EntityManager em;

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private NotificationService notificationService;

    private final LocalDate nextDay = LocalDate.now();

    @BeforeEach
    void enableStatistics() {
        SessionFactoryImplementor sessionFactory =
                emf.unwrap(SessionFactoryImplementor.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
    }

    @Test
    void countQueriesCreateNextDayDiaries() {
        SessionFactoryImplementor sessionFactory =
                emf.unwrap(SessionFactoryImplementor.class);
        Statistics stats = sessionFactory.getStatistics();

        stats.clear();
        diaryService.createNextDayDiaries(nextDay);
        long before = stats.getPrepareStatementCount();
        System.out.println("기존 쿼리 수: " + before);
    }

    @Test
    void countQueriesUpdateNotificationTime() {
        SessionFactoryImplementor sessionFactory =
                emf.unwrap(SessionFactoryImplementor.class);
        Statistics stats = sessionFactory.getStatistics();

        stats.clear();
        notificationService.updateNotificationTime(nextDay);
        em.flush();
        long before = stats.getPrepareStatementCount();
        System.out.println("기존 쿼리 수: " + before);
    }

    @Test
    void countQueriesCreateNextDayDiaryAndNotificationTime() {
        SessionFactoryImplementor sessionFactory =
                emf.unwrap(SessionFactoryImplementor.class);
        Statistics stats = sessionFactory.getStatistics();

        schedulerService.createNextDayDiaryAndNotificationTime();
        em.flush();
        long before = stats.getPrepareStatementCount();
        System.out.println("기존 쿼리 수: " + before);
    }
}
