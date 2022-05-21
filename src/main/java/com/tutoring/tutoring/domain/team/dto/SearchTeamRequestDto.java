package com.tutoring.tutoring.domain.team.dto;

import lombok.Value;

@Value
public class SearchTeamRequestDto {
    private final String query;
    private final String requirement;
}
