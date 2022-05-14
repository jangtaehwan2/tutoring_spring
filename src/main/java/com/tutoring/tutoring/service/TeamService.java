package com.tutoring.tutoring.service;

import com.tutoring.tutoring.AuthManager;
import com.tutoring.tutoring.domain.joinrequest.JoinRequest;
import com.tutoring.tutoring.domain.joinrequest.dto.AcceptJoinRequestResponseDto;
import com.tutoring.tutoring.domain.joinrequest.dto.CreateJoinRequestResponseDto;
import com.tutoring.tutoring.domain.joinrequest.dto.JoinRequestDto;
import com.tutoring.tutoring.domain.joinrequest.dto.RejectJoinRequestResponseDto;
import com.tutoring.tutoring.domain.subscription.Subscription;
import com.tutoring.tutoring.domain.team.Team;
import com.tutoring.tutoring.domain.team.dto.CreateTeamResponseDto;
import com.tutoring.tutoring.domain.team.dto.TeamDto;
import com.tutoring.tutoring.domain.team.dto.ReadTeamListDto;
import com.tutoring.tutoring.domain.user.User;
import com.tutoring.tutoring.repository.JoinRequestRepository;
import com.tutoring.tutoring.repository.SubscriptionRepository;
import com.tutoring.tutoring.repository.TeamRepository;
import com.tutoring.tutoring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final JoinRequestRepository joinRequestRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final AuthManager authManager;

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

        // 팀 생성 시 호스트 자동 가입
        Subscription subscription = Subscription.builder()
                .user(userRepository.findById(userId).get())
                .team(savedTeam)
                .build();
        subscriptionRepository.save(subscription);

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

    /**
     * 팀 참여 로직
     * public 팀이면 요청과 동시에 수락하기
     * @param teamId
     * @param userId
     * @param description
     * @return
     */
    public CreateJoinRequestResponseDto createJoinRequest(long teamId, long userId, String description) {
        /**
         * 이미 요청 또는 구독 중이면 수행하지 않고 message fail 반환
         * 이외에는 성공적으로 요청하고 메세지 반환
         */
        Optional<JoinRequest> joinRequest = joinRequestRepository.findByTeamIdAndUserId(teamId, userId);
        Optional<Subscription> subscription = subscriptionRepository.findByTeamIdAndUserId(teamId, userId);

        if(joinRequest.isEmpty() && subscription.isEmpty()) {
            if(authManager.isPublicTeam(teamId)) {
                // PUBLIC team 이면 바로 가입 로직을 실행
                Subscription newSubscription = Subscription.builder()
                        .team(teamRepository.findById(teamId).get())
                        .user(userRepository.findById(userId).get())
                        .build();
                Subscription savedSubscription = subscriptionRepository.save(newSubscription);
                return CreateJoinRequestResponseDto.builder()
                        .message("Subscribed")
                        .requestId(0)
                        .teamId(savedSubscription.getTeam().getId())
                        .userId(savedSubscription.getUser().getId())
                        .build();
            }
            JoinRequest newJoin = JoinRequest.builder()
                    .team(teamRepository.findById(teamId).get())
                    .description(description)
                    .user(userRepository.findById(userId).get())
                    .build();
            JoinRequest savedJoinRequest = joinRequestRepository.save(newJoin);

            return CreateJoinRequestResponseDto.builder()
                    .message("Requested to join")
                    .requestId(savedJoinRequest.getId())
                    .teamId(savedJoinRequest.getTeam().getId())
                    .userId(savedJoinRequest.getUser().getId())
                    .build();
        } else {
            return CreateJoinRequestResponseDto.builder()
                    .message("Already subscription or requested to join")
                    .requestId(-1)
                    .teamId(teamId)
                    .userId(userId)
                    .build();
        }
    }

    /**
     * 팀 참여 요청 리스트 로직 * 호스트만 사용 가능 *
     * @param teamId
     * @return
     */
    public List<JoinRequestDto> readJoinRequest(long teamId, long userId) {
        if(!authManager.isHost(teamId, userId)) {
            return null;
        } else {
            List<JoinRequest> joinRequestList = joinRequestRepository.findByTeamId(teamId);
            List<JoinRequestDto> joinRequestDtoList = new ArrayList<>();
            for (JoinRequest joinRequest : joinRequestList) {
                joinRequestDtoList.add(JoinRequestDto.builder()
                        .joinRequest(joinRequest)
                        .build());
            }
            return joinRequestDtoList;
        }
    }

    public AcceptJoinRequestResponseDto acceptJoinRequest(long joinId) {
        JoinRequest joinRequest = joinRequestRepository.findById(joinId).get();
        Team team = joinRequest.getTeam();
        User user = joinRequest.getUser();
        Subscription subscription = Subscription.builder()
                .user(user)
                .team(team)
                .build();
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        joinRequestRepository.delete(joinRequest);
        return AcceptJoinRequestResponseDto.builder()
                .subscription(savedSubscription)
                .build();
    }

    public RejectJoinRequestResponseDto rejectJoinRequest(long joinId) {
        JoinRequest joinRequest = joinRequestRepository.findById(joinId).get();
        joinRequestRepository.delete(joinRequest);
        return RejectJoinRequestResponseDto.builder()
                .message("JoinRequest Rejected")
                .build();
    }
}
