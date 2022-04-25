package com.tutoring.tutoring.domain.team.dto;

import com.tutoring.tutoring.domain.team.TeamType;
import com.tutoring.tutoring.domain.teamprofile.TeamProfile;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor
public class TeamResponseDto {
    private long id;
    private String name;
    private String tag;
    private String description;
    private TeamType type;
    private long hostId;
    private TeamProfile wanted;
}
