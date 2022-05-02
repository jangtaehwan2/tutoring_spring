package com.tutoring.tutoring.controller;

import com.tutoring.tutoring.domain.AuthManager;
import com.tutoring.tutoring.domain.team.TeamType;
import com.tutoring.tutoring.domain.team.dto.CreateTeamRequestDto;
import com.tutoring.tutoring.domain.team.dto.CreateTeamResponseDto;
import com.tutoring.tutoring.domain.team.dto.ReadTeamListDto;
import com.tutoring.tutoring.domain.team.dto.TeamDto;
import com.tutoring.tutoring.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/team")
@RequiredArgsConstructor
@RestController
public class TeamController {
    /**
     * 1. 팀 생성
     * 2. 팀 목록 읽기 (public, private-profileIsOpen)
     * 3. 팀 읽기(단일)
     * 4. 팀 포스트 읽기
     * 5. public 포스트 읽기
     * 6. 팀 조인
     * 7. 팀 수정
     * 8. 팀 오픈 클로즈
     */

    private final TeamService teamService;
    private final AuthManager authManager;

    /**
     * 팀 생성
     * @param createTeamRequestDto
     * @param token
     * @return
     */
    @PostMapping("")
    public ResponseEntity<CreateTeamResponseDto> createTeam(@Valid @RequestBody CreateTeamRequestDto createTeamRequestDto, @RequestHeader(name = "Authorization")String token) {
        try {
            System.out.println(createTeamRequestDto.toString());
            long userId = authManager.extractUserId(token);
            List<String> tags = createTeamRequestDto.getTags();
            String name = createTeamRequestDto.getName();
            String teamType = createTeamRequestDto.getTeamType();
            CreateTeamResponseDto team = teamService.createTeam(userId, tags, name, teamType);
            return ResponseEntity.status(HttpStatus.OK).body(team);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 팀 리스트 읽기 (isClosed == False)
     * @return
     */
    @GetMapping("")
    public ResponseEntity<ReadTeamListDto> readTeamList() {
        try {
            ReadTeamListDto readTeamListDto = teamService.readTeamList();
            return ResponseEntity.status(HttpStatus.OK).body(readTeamListDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDto> readTeam(@PathVariable(name = "teamId")long teamId) {
        try{
            TeamDto teamDto = teamService.readTeam(teamId);
            return ResponseEntity.status(HttpStatus.OK).body(teamDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
