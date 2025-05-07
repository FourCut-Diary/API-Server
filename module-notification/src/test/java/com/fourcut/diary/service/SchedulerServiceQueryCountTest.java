package com.fourcut.diary.service;

import com.fourcut.diary.diary.service.DiaryService;
import com.fourcut.diary.notification.domain.Notification;
import com.fourcut.diary.notification.repository.NotificationRepository;
import com.fourcut.diary.notification.service.NotificationService;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    private final LocalDate nextDay = LocalDate.now();

    @BeforeEach
    void setUpDummyDataAndEnableStats() {
        SessionFactoryImplementor sf = emf.unwrap(SessionFactoryImplementor.class);
        sf.getStatistics().setStatisticsEnabled(true);

        notificationRepository.deleteAll();
        userRepository.deleteAll();
        em.flush(); em.clear();

        List<User> users = IntStream.rangeClosed(1, 1000)
                .mapToObj(i -> com.fourcut.diary.user.domain.User.newInstance(
                        "social" + i,
                        "nick"   + i,
                        LocalDate.of(1990, 1, 1),
                        com.fourcut.diary.user.domain.Gender.MALE,
                        LocalTime.of(8, 0),
                        LocalTime.of(20, 0),
                        "arn"    + i,
                        "token"  + i
                ))
                .collect(Collectors.toList());
        userRepository.saveAll(users);
        em.flush(); em.clear();

        List<Notification> notis = users.stream()
                .map(u -> {
                    List<LocalDateTime> slots = Arrays.asList(
                            nextDay.atTime( 9,  0),
                            nextDay.atTime(12,  0),
                            nextDay.atTime(15,  0),
                            nextDay.atTime(18,  0)
                    );

                    return Notification.builder()
                            .firstTimeSlot (slots.get(0))
                            .secondTimeSlot(slots.get(1))
                            .thirdTimeSlot (slots.get(2))
                            .fourthTimeSlot(slots.get(3))
                            .user(u)
                            .build();
                })
                .collect(Collectors.toList());
        notificationRepository.saveAll(notis);
        em.flush(); em.clear();
    }

    @Test
    void countQueriesCreateNextDayDiaries() {
        Statistics stats = emf.unwrap(SessionFactoryImplementor.class).getStatistics();
        stats.clear();

        diaryService.createNextDayDiaries(nextDay);
        em.flush();

        long q = stats.getPrepareStatementCount();
        System.out.println("createNextDayDiaries 쿼리 수: " + q);
    }

    @Test
    void countQueriesUpdateNotificationTime() {
        Statistics stats = emf.unwrap(SessionFactoryImplementor.class).getStatistics();
        stats.clear();

        notificationService.updateNotificationTime(nextDay);
        em.flush();

        long q = stats.getPrepareStatementCount();
        System.out.println("updateNotificationTime 쿼리 수: " + q);
    }
}

