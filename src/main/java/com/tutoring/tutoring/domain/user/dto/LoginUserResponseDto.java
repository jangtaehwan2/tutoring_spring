package com.tutoring.tutoring.domain.user.dto;

import com.tutoring.tutoring.domain.userprofile.dto.UserProfileResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginUserResponseDto {
    private String token;
    private long id;
    private String userName;
    private String userNickname;
    private UserProfileResponseDto userProfile;

    @Builder
    public LoginUserResponseDto(String token, long id, String userName, String userNickname, UserProfileResponseDto userProfile) {
        this.token = token;
        this.id = id;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userProfile = userProfile;
    }
}
