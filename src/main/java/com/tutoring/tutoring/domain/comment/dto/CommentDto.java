package com.tutoring.tutoring.domain.comment.dto;

import com.tutoring.tutoring.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDto {
    private long id;
    private String description;
    private UserDto user;

    @Builder
    public CommentDto(long id, String description, UserDto user) {
        this.id = id;
        this.description = description;
        this.user = user;
    }
}
