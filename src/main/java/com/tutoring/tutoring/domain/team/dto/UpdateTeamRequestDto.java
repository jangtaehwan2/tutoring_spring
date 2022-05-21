package com.tutoring.tutoring.domain.team.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@Getter
@NoArgsConstructor
public class UpdateTeamRequestDto {
    private String description;
    private String isClosed;
}
