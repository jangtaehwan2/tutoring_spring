package com.tutoring.tutoring;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tutoring.tutoring.domain.subscription.Subscription;
import com.tutoring.tutoring.domain.team.Team;
import com.tutoring.tutoring.repository.PostRepository;
import com.tutoring.tutoring.repository.SubscriptionRepository;
import com.tutoring.tutoring.repository.TeamRepository;
import com.tutoring.tutoring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthManager {
    /**
     * Token 유효성, 권한 관리를 위한 메소드를 관리하는 클래스
     */

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final SubscriptionRepository subscriptionRepository;

    /**
     * JWT 시크릿키 지정 및 알고리즘 생성
     */
    public final static Algorithm algorithm = Algorithm.HMAC256("JWTSecretKey");
    public final static String issuer = "JTH";

    // JWT 검사
    private final JWTVerifier jwtVerifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build();

    // token 과 RequestBody userId가 일치하는지 확인
    // extractUserId 메소드로 대체
    public boolean identify(String token, long userId) {
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        long tokenId = decodedJWT.getClaim("userId").asLong();
        if(tokenId == userId) {
            return true;
        }
        else {
           return false;
        }
    }

    // JWT userId 추출
    public long extractUserId(String token) {
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            long tokenId = decodedJWT.getClaim("userId").asLong();
            return tokenId;
        } catch(Exception e) {
            throw e;
        }
    }

    /**
     * Team Host 검증
     * Team:Host -> return true
     * @param teamId
     * @param userId
     * @return
     */
    public boolean isHost(long teamId, long userId) {
        Team team = teamRepository.findById(teamId).get();
        if(userId == team.getHost().getId()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Team TYPE 검증
     * public -> return true
     * @param teamId
     * @return
     */
    public boolean isPublicTeam(long teamId) {
        Team team = teamRepository.findById(teamId).get();
        if(team.getType().equals("PUBLIC")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Team 의 멤버가 맞는지 검증
     * @param teamId
     * @param userId
     * @return
     */
    public boolean isMember(long teamId, long userId) {
        try {
            if(subscriptionRepository.findByTeamIdAndUserId(teamId, userId).isEmpty()) {
                throw new Exception("NotMemberException");
            } else {
                return true;
            }
        } catch(Exception e) {
            // 추후에 로그로 바꿀 것
            System.out.println(userId + " is not member " + teamId);
        }
        // 문법때문에 꼭 필요?
        return false;
    }
}
