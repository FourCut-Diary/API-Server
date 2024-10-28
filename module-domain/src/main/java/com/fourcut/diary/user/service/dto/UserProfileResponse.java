package com.fourcut.diary.user.service.dto;

public record UserProfileResponse(

        Long userId,

        String profileImage,

        String nickname,

        Long daysAfterRegistration
) {
}
