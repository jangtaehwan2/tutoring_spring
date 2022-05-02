package com.tutoring.tutoring.domain.post.dto;

import com.tutoring.tutoring.domain.team.dto.TeamDto;
import com.tutoring.tutoring.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadPostDto {
    private PostDto post;
    private TeamDto team;
    private UserDto user;

    @Builder
    public ReadPostDto(PostDto post, TeamDto team, UserDto user) {
        this.post = post;
        this.team = team;
        this.user = user;
    }
}
