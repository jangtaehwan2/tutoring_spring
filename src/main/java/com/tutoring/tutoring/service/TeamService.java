package com.tutoring.tutoring.service;

import com.tutoring.tutoring.domain.team.Team;
import com.tutoring.tutoring.domain.team.dto.CreateTeamResponseDto;
import com.tutoring.tutoring.domain.team.dto.TeamDto;
import com.tutoring.tutoring.domain.team.dto.ReadTeamListDto;
import com.tutoring.tutoring.domain.user.User;
import com.tutoring.tutoring.repository.TeamRepository;
import com.tutoring.tutoring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    private String tagListToString(List<String> tags) {
        String tag = "";
        for (String s : tags) {
            tag += s + ",";
        }
        return tag;
    }
    private List<String> tagStringToList(String tag) {
        String[] t = tag.split(",");
        return Arrays.asList(t);
    }

    /**
     * 팀 생성 로직
     * @param userId
     * @param tags
     * @param name
     * @param teamType
     * @return
     */
    public CreateTeamResponseDto createTeam(long userId, List<String> tags, String name, String teamType) {
        User host = userRepository.findById(userId).get();
        String tag = tagListToString(tags);
        Team team = Team.builder()
                .host(host)
                .tag(tag)
                .name(name)
                .type(teamType)
                .build();
        Team savedTeam = teamRepository.save(team);

        List<String> savedTags = tagStringToList(savedTeam.getTag());

        CreateTeamResponseDto createTeamResponseDto= CreateTeamResponseDto.builder()
                .teamId(savedTeam.getId())
                .hostId(savedTeam.getHost().getId())
                .name(savedTeam.getName())
                .tags(savedTags)
                .teamType(savedTeam.getType())
                .build();

        return createTeamResponseDto;
    }

    public ReadTeamListDto readTeamList() {
        List<Team> teams = teamRepository.findAllByIsClosedFalse();
        List<TeamDto> ReadTeams = new ArrayList<>();
        for (Team team : teams) {
            ReadTeams.add(new TeamDto(team));
        }
        return new ReadTeamListDto(ReadTeams);
    }

    public TeamDto readTeam(long teamId) {
        Team team = teamRepository.findById(teamId).get();
        TeamDto teamDto = TeamDto.builder()
                .team(team)
                .build();
        return teamDto;
    }
}
