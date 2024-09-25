package com.fourcut.diary.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String socialId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private LocalTime dailyStartTime;

    @Column(nullable = false)
    private LocalTime dailyEndTime;

    public static User newInstance(String socialId, String nickname, LocalDate birthday, Gender gender, LocalTime dailyStartTime, LocalTime dailyEndTime) {
        return User.builder()
                .socialId(socialId)
                .nickname(nickname)
                .birthday(birthday)
                .gender(gender)
                .dailyStartTime(dailyStartTime)
                .dailyEndTime(dailyEndTime)
                .build();
    }
}
