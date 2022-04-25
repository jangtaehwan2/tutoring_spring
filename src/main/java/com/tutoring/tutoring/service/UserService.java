package com.tutoring.tutoring.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.tutoring.tutoring.domain.user.User;
import com.tutoring.tutoring.domain.user.dto.CreateUserRequestDto;
import com.tutoring.tutoring.domain.user.dto.CreateUserResponseDto;
import com.tutoring.tutoring.domain.user.dto.LoginUserResponseDto;
import com.tutoring.tutoring.domain.userprofile.UserProfile;
import com.tutoring.tutoring.domain.userprofile.dto.UserProfileResponseDto;
import com.tutoring.tutoring.repository.UserProfileRepository;
import com.tutoring.tutoring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    /**
     * JWT 시크릿키 지정 및 알고리즘 생성
     */
    private Algorithm algorithm = Algorithm.HMAC256("JWTSecretKey");
    private String issuer = "JTH";

    // JWT 검사
    private JWTVerifier jwtVerifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build();

    /**
     * 로그인 로직
     * JWT token 반환, 토큰 유효시간 24시간
     * @param userName
     * @param userPassword
     * @return
     */
    public LoginUserResponseDto loginUser(String userName, String userPassword) {
            User user = userRepository.findByUserNameAndUserPassword(userName, userPassword).get();
            String token = JWT.create()
                    .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .withIssuer(issuer)
                .withClaim("userName", user.getUserName())
                .sign(algorithm);
            UserProfile userProfile = userProfileRepository.findByUserId(user.getId()).get();
            UserProfileResponseDto userProfileResponseDto = UserProfileResponseDto.builder()
                            .userProfile(userProfile)
                            .build();
        return LoginUserResponseDto.builder()
                .token(token)
                .id(user.getId())
                .userName(user.getUserName())
                .userNickname(user.getUserNickname()).
                userProfile(userProfileResponseDto)
                .build();
    }


    /**
     * 회원가입 로직
     * @param userName
     * @param userNickname
     * @param userPassword
     * @return
     */
    public CreateUserResponseDto createUser(String userName, String userNickname, String userPassword) {
        User newUser = User.builder()
                .userName(userName)
                .userNickname(userNickname)
                .userPassword(userPassword)
                .build();
        User user = userRepository.save(newUser);
        UserProfile newUserProfile = UserProfile.builder()
                .user(user)
                .build();
        userProfileRepository.save(newUserProfile);
        return CreateUserResponseDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .userNickname(user.getUserNickname())
                .build();
    }
}
