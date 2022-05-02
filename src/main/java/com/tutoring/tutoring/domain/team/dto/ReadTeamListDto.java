package com.tutoring.tutoring.domain.team.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReadTeamListDto {
    List<TeamDto> teams = new ArrayList<>();

    public ReadTeamListDto(List<TeamDto> teams) {
        this.teams = teams;
    }
}
