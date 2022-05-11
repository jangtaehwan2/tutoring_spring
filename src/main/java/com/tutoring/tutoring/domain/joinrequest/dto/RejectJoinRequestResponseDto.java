package com.tutoring.tutoring.domain.joinrequest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RejectJoinRequestResponseDto {
    private String message;

    @Builder
    public RejectJoinRequestResponseDto(String message) {
        this.message = message;
    }
}
