package com.tutoring.tutoring.domain.joinrequest.dto;

import com.tutoring.tutoring.domain.subscription.Subscription;
import com.tutoring.tutoring.domain.team.dto.TeamDto;
import com.tutoring.tutoring.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AcceptJoinRequestResponseDto {
    private TeamDto team;

    @Builder
    public AcceptJoinRequestResponseDto(Subscription subscription) {
        this.team = new TeamDto(subscription.getTeam());
    }
}
