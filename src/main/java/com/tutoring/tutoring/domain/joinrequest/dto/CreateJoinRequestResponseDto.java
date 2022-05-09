package com.tutoring.tutoring.domain.joinrequest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateJoinRequestResponseDto {
    private String message;
    private long requestId;
    private long teamId;
    private long userId;

    @Builder
    public CreateJoinRequestResponseDto(String message, long requestId, long teamId, long userId) {
        this.message = message;
        this.requestId = requestId;
        this.teamId = teamId;
        this.userId = userId;
    }
}
