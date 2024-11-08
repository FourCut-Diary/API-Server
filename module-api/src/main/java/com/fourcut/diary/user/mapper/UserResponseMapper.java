package com.fourcut.diary.user.mapper;

import com.fourcut.diary.user.dto.response.UserProfileResponse;
import com.fourcut.diary.user.service.dto.UserProfileDto;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public UserProfileResponse toUserProfileResponse(UserProfileDto userProfileDto) {

        return new UserProfileResponse(
                userProfileDto.userId(),
                userProfileDto.profileImage(),
                userProfileDto.nickname(),
                userProfileDto.daysAfterRegistration()
        );
    }
}
