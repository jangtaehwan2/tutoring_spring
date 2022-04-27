package com.tutoring.tutoring.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteUserResponseDto {
    private String message;

    @Builder
    public DeleteUserResponseDto(String message) {
        this.message = message;
    }
}
