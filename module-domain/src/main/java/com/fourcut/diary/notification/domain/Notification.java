package com.fourcut.diary.notification.domain;

import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.exception.model.BadRequestException;
import com.fourcut.diary.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime firstTimeSlot;

    @Column(nullable = false)
    private LocalDateTime secondTimeSlot;

    @Column(nullable = false)
    private LocalDateTime thirdTimeSlot;

    @Column(nullable = false)
    private LocalDateTime fourthTimeSlot;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void updateTimeSlot(List<LocalDateTime> timeSlots) {
        this.firstTimeSlot = timeSlots.get(0);
        this.secondTimeSlot = timeSlots.get(1);
        this.thirdTimeSlot = timeSlots.get(2);
        this.fourthTimeSlot = timeSlots.get(3);
    }

    public boolean isPossibleEnrollPicture(LocalDateTime now, Integer index) {
        LocalDateTime targetTimeSlot = switch (index) {
            case 1 -> this.firstTimeSlot;
            case 2 -> this.secondTimeSlot;
            case 3 -> this.thirdTimeSlot;
            case 4 -> this.fourthTimeSlot;
            default -> throw new BadRequestException(ErrorMessage.INVALID_PICTURE_INDEX);
        };

        LocalDateTime endTime = targetTimeSlot.plusMinutes(20);

        return !now.isBefore(targetTimeSlot) && !now.isAfter(endTime);
    }

    public boolean isFinished() {
        return fourthTimeSlot.plusMinutes(20).isBefore(LocalDateTime.now());
    }
}
