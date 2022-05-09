package com.tutoring.tutoring.domain.joinrequest.dto;

import com.tutoring.tutoring.domain.joinrequest.JoinRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinRequestDto {
    private long requestId;
    private long userId;
    private long teamId;
    private String description;

    @Builder
    public JoinRequestDto(JoinRequest joinRequest) {
        this.requestId = joinRequest.getId();
        this.userId = joinRequest.getUser().getId();
        this.teamId = joinRequest.getTeam().getId();
        this.description = joinRequest.getDescription();
    }
}
