package com.tutoring.tutoring.domain.team.dto;

import com.tutoring.tutoring.domain.team.TeamType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class CreateTeamRequestDto {
    @Size(min = 1, max = 20)
    private String name;
    private List<String> tags;
    private String teamType;
}
