package com.tutoring.tutoring.controller;

import com.tutoring.tutoring.domain.AuthManager;
import com.tutoring.tutoring.domain.joinrequest.dto.CreateJoinRequestRequestDto;
import com.tutoring.tutoring.domain.joinrequest.dto.CreateJoinRequestResponseDto;
import com.tutoring.tutoring.domain.joinrequest.dto.JoinRequestDto;
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
     * 1. 팀 생성 1
     * 2. 팀 목록 읽기 (public, private-profileIsOpen) 1
     * 3. 팀 읽기(단일) 1
     * 4. 팀 조인 1
     * 팀 조인(요청) 목록 보기
     * 5. 팀 수정
     * 6. 팀 오픈 클로즈
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

    /**
     * 팀 읽기(단일)
     * @param teamId
     * @return
     */
    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDto> readTeam(@PathVariable(name = "teamId")long teamId) {
        try{
            TeamDto teamDto = teamService.readTeam(teamId);
            return ResponseEntity.status(HttpStatus.OK).body(teamDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 아마도 메세지 리턴으로 갈 듯
    @PostMapping("/{teamId}/join")
    public ResponseEntity<CreateJoinRequestResponseDto> createJoinRequest(@PathVariable(name = "teamId")long teamId, @RequestHeader(name = "Authorization")String token, @RequestBody CreateJoinRequestRequestDto createJoinRequestRequestDto){
        try {
            long userId = authManager.extractUserId(token);
            String description = createJoinRequestRequestDto.getDescription();
            CreateJoinRequestResponseDto responseDto = teamService.createJoinRequest(teamId, userId, description);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 팀의 호스트만 사용 할 수 있어야 함
    @GetMapping("/{teamId}/join")
    public ResponseEntity<List<JoinRequestDto>> readJoinRequest(@PathVariable(name = "teamId")long teamId, @RequestHeader(name = "Authorization")String token){
        try {
            long userId = authManager.extractUserId(token); // 토큰 호스트 검사 로직
            List<JoinRequestDto> responseDto = teamService.readJoinRequest(teamId, userId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}