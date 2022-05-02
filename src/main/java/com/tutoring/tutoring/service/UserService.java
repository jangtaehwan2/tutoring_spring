package com.tutoring.tutoring.service;

import com.auth0.jwt.JWT;
import com.tutoring.tutoring.domain.AuthManager;
import com.tutoring.tutoring.domain.user.User;
import com.tutoring.tutoring.domain.user.dto.*;
import com.tutoring.tutoring.domain.userprofile.UserProfile;
import com.tutoring.tutoring.domain.userprofile.dto.UserProfileDto;
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
                .withIssuer(AuthManager.issuer)
                .withClaim("userId", user.getId())
                .withClaim("userName", user.getUserName())
                .withClaim("userNickname", user.getUserNickname())
                .sign(AuthManager.algorithm);
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

    public UserDto readUser(long userId) {
        User user = userRepository.findById(userId).get();
        UserProfile userProfile = userProfileRepository.findByUserId(userId).get();
        return UserDto.builder()
                .user(user)
                .userProfile(UserProfileDto.builder()
                        .userProfile(userProfile)
                        .build())
                .build();
    }

    /**
     * 회원정보 수정 로직(닉네임, 비밀번호)
     * @param userId
     * @param userNickname
     * @param userPassword
     * @return
     */
    public UpdateUserResponseDto updateUser(long userId, String userNickname, String userPassword) {
        User user = userRepository.findById(userId).get();
        user.updateUser(userNickname, userPassword);
        User updatedUser = userRepository.save(user);
        return UpdateUserResponseDto.builder()
                .userId(updatedUser.getId())
                .userName(updatedUser.getUserName())
                .userNickname(updatedUser.getUserNickname())
                .build();
    }

    /**
     * 회원프로필 수정 로직(description only, 추후 프로필 이미지 추가 예정)
     * @param userId
     * @param description
     * @return
     */
    public UserProfileResponseDto updateUserProfile(long userId, String description) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId).get();
        userProfile.updateDescription(description);
        UserProfile updatedUserProfile = userProfileRepository.save(userProfile);
        return UserProfileResponseDto.builder()
                .userProfile(updatedUserProfile)
                .build();
    }

    /**
     * 회원삭제 로직
     * @param userId
     * @return
     */
    public DeleteUserResponseDto deleteUser(long userId) {
        User user = userRepository.findById(userId).get();
        UserProfile userProfile = userProfileRepository.findByUserId(userId).get();
        userProfileRepository.delete(userProfile);
        userRepository.delete(user);
        return DeleteUserResponseDto.builder()
                .message("User Deleted")
                .build();
    }
}
