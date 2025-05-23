package com.fourcut.diary.diary.domain;

import com.fourcut.diary.common.AuditingTimeEntity;
import com.fourcut.diary.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Diary extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(nullable = false)
    private LocalDate date;

    @Column
    private String firstPicture;

    @Column
    private String secondPicture;

    @Column
    private String thirdPicture;

    @Column
    private String fourthPicture;

    @Column
    private String firstComment;

    @Column
    private String secondComment;

    @Column
    private String thirdComment;

    @Column
    private String fourthComment;

    @Column
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void enrollTitle(String title) {
        this.title = title;
    }

    public void enrollDailyPicture(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
