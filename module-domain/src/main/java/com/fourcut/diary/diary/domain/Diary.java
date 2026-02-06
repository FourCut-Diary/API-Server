package com.fourcut.diary.diary.domain;

import com.fourcut.diary.common.AuditingTimeEntity;
import com.fourcut.diary.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime firstCaptureTime;

    @Column
    private LocalDateTime secondCaptureTime;

    @Column
    private LocalDateTime thirdCaptureTime;

    @Column
    private LocalDateTime fourthCaptureTime;

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

    public boolean hasPictureAt(int index) {
        return switch (index) {
            case 1 -> this.firstPicture != null;
            case 2 -> this.secondPicture != null;
            case 3 -> this.thirdPicture != null;
            case 4 -> this.fourthPicture != null;
            default -> false;
        };
    }
}
