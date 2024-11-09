package com.fourcut.diary.user.service.dto;

public record UserProfileDto(

        Long userId,

        String profileImage,

        String nickname,

        Long daysAfterRegistration
) {
}
