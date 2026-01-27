package com.fourcut.diary.user.domain;

import com.fourcut.diary.common.AuditingTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String socialId;

    @Column(nullable = false)
    private String nickname;

    @Column
    private String profileImage;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private LocalTime dailyStartTime;

    @Column(nullable = false)
    private LocalTime dailyEndTime;

    @Column(nullable = false)
    private String snsEndpointArn;

    @Column(nullable = false)
    private String fcmToken;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType role;

    public static User newInstance(String socialId, String nickname, LocalDate birthday, Gender gender, LocalTime dailyStartTime, LocalTime dailyEndTime, String profileImage, String snsEndpointArn, String fcmToken) {
        return User.builder()
                .socialId(socialId)
                .nickname(nickname)
                .birthday(birthday)
                .gender(gender)
                .dailyStartTime(dailyStartTime)
                .dailyEndTime(dailyEndTime)
                .profileImage(profileImage)
                .snsEndpointArn(snsEndpointArn)
                .fcmToken(fcmToken)
                .role(RoleType.USER)
                .build();
    }

    public boolean isDifferentFcmToken(String fcmToken) {
        return this.fcmToken != null && !this.fcmToken.equals(fcmToken);
    }

    public void updateSnsEndpoint(String snsEndpointArn) {
        this.snsEndpointArn = snsEndpointArn;
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public boolean isOverDay() {
        return dailyStartTime.isAfter(dailyEndTime);
    }
}
