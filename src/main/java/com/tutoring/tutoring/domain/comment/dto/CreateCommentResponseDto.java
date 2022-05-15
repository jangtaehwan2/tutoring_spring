package com.tutoring.tutoring.domain.comment.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateCommentResponseDto {
    private long id;
    private String description;

    @Builder
    public CreateCommentResponseDto(long id, String description) {
        this.id = id;
        this.description = description;
    }
}
