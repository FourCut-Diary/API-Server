package com.fourcut.diary.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public static User newInstance(String socialId, String name, String nickname, LocalDate birthday, Gender gender) {
        return User.builder()
                .socialId(socialId)
                .name(name)
                .nickname(nickname)
                .birthday(birthday)
                .gender(gender)
                .build();
    }
}
