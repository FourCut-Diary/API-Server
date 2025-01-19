package com.fourcut.diary.user.domain.notification;

import com.fourcut.diary.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalDateTime firstTimeSlot;

    @Column(nullable = false)
    private LocalDateTime secondTimeSlot;

    @Column(nullable = false)
    private LocalDateTime thirdTimeSlot;

    @Column(nullable = false)
    private LocalDateTime fourthTimeSlot;

    public void updateNotificationTime(List<LocalDateTime> randomTimes){
        firstTimeSlot = randomTimes.get(0);
        secondTimeSlot = randomTimes.get(1);
        thirdTimeSlot = randomTimes.get(2);
        fourthTimeSlot = randomTimes.get(3);
    }
}
