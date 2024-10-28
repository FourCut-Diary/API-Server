package com.fourcut.diary.user.dto.response;

public record UserProfileResponse(

        Long userId,

        String profileImage,

        String name,

        Integer daysAfterRegistration
) {
}
