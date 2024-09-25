package com.fourcut.diary.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDailyPushNotificationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime firstTimeSlot;

    @Column(nullable = false)
    private LocalDateTime secondTimeSlot;

    @Column(nullable = false)
    private LocalDateTime thirdTimeSlot;

    @Column(nullable = false)
    private LocalDateTime fourthTimeSlot;
}
