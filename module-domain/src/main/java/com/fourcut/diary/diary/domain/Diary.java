package com.fourcut.diary.diary.domain;

import com.fourcut.diary.common.AuditingTimeEntity;
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

    @Column(nullable = false)
    private LocalDateTime firstTimeSlot;

    @Column(nullable = false)
    private LocalDateTime secondTimeSlot;

    @Column(nullable = false)
    private LocalDateTime thirdTimeSlot;

    @Column(nullable = false)
    private LocalDateTime fourthTimeSlot;

    @Column
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void updateTimeSlot(List<LocalDateTime> timeSlots) {
        this.firstTimeSlot = timeSlots.get(0);
        this.secondTimeSlot = timeSlots.get(1);
        this.thirdTimeSlot = timeSlots.get(2);
        this.fourthTimeSlot = timeSlots.get(3);
    }
}
