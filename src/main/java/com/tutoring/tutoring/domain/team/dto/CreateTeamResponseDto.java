package com.tutoring.tutoring.domain.team.dto;

import com.tutoring.tutoring.domain.team.TeamType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateTeamResponseDto {
    private long teamId;
    private long hostId;
    private String name;
    private List<String> tags;
    private String teamType;

    @Builder
    public CreateTeamResponseDto(long teamId, long hostId, String name, List<String> tags, String teamType) {
        this.teamId = teamId;
        this.hostId = hostId;
        this.name = name;
        this.tags = tags;
        this.teamType = teamType;
    }
}
