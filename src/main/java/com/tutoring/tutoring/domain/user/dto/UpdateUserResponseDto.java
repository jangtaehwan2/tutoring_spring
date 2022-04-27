package com.tutoring.tutoring.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateUserResponseDto {
    private long userId;
    private String userName;
    private String userNickname;

    @Builder
    public UpdateUserResponseDto(long userId, String userName, String userNickname) {
        this.userId = userId;
        this.userName = userName;
        this.userNickname = userNickname;
    }
}
