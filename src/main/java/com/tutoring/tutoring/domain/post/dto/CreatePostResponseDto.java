package com.tutoring.tutoring.domain.post.dto;

import com.tutoring.tutoring.domain.team.Team;
import com.tutoring.tutoring.domain.team.dto.TeamDto;
import com.tutoring.tutoring.domain.user.User;
import com.tutoring.tutoring.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class CreatePostResponseDto {
    private long id;
    private String title;
    private List<String> tags;
    private UserDto user;
    private TeamDto team;

    @Builder
    public CreatePostResponseDto(long id, String title, List<String> tags, UserDto user, TeamDto team) {
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.user = user;
        this.team = team;
    }
}
