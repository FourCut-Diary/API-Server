package com.fourcut.diary.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String socialId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Builder
    public User newInstance(String socialId, String name, String nickname, LocalDate birthday, Gender gender) {
        return new User(socialId, name, nickname, birthday, gender);
    }

    private User(String socialId, String name, String nickname, LocalDate birthday, Gender gender) {
        this.socialId = socialId;
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
    }
}
