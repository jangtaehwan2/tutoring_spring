package com.tutoring.tutoring.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class CreateUserResponseDto {
    private long id;
    private String userName;
    private String userNickname;

    @Builder
    public CreateUserResponseDto(long id, String userName, String userNickname) {
        this.id = id;
        this.userName = userName;
        this.userNickname = userNickname;
    }
}
