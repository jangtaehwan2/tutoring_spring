package com.tutoring.tutoring.controller;

import com.tutoring.tutoring.AuthManager;
import com.tutoring.tutoring.domain.team.dto.TeamDto;
import com.tutoring.tutoring.domain.user.dto.*;
import com.tutoring.tutoring.domain.userprofile.dto.UpdateUserProfileRequestDto;
import com.tutoring.tutoring.domain.userprofile.dto.UserProfileResponseDto;
import com.tutoring.tutoring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final AuthManager authManager;

    /**
     * 로그인
     * @param loginUserRequestDto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> loginUser(@Valid @RequestBody LoginUserRequestDto loginUserRequestDto) {
        try {
            String userName = loginUserRequestDto.getUserName();
            String userPassword = loginUserRequestDto.getUserPassword();
            LoginUserResponseDto loginUserResponseDto = userService.loginUser(userName, userPassword);
            return ResponseEntity.status(HttpStatus.OK).body(loginUserResponseDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 회원가입
     * @param createUserRequestDto
     * @return
     */
    @PostMapping("")
    public ResponseEntity<CreateUserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto) {
        try{
            String userName = createUserRequestDto.getUserName();
            String userNickname = createUserRequestDto.getUserNickname();
            String userPassword = createUserRequestDto.getUserPassword();
            CreateUserResponseDto createUserResponseDto = userService.createUser(userName, userNickname, userPassword);
            return ResponseEntity.status(HttpStatus.OK).body(createUserResponseDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 유저 조회
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> readUser(@PathVariable(name = "userId")long userId) {
        try{
            UserDto userDto = userService.readUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/{userId}/subscription")
    public ResponseEntity<List<TeamDto>> readSubscription(@PathVariable(name = "userId")long userId) {
        try {
            List<TeamDto> teamList = userService.readSubscriptionList(userId);
            return ResponseEntity.status(HttpStatus.OK).body(teamList);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 회원수정 (닉네임, 패스워드)
     * 본인인지 JWT 확인하는 로직 필요
     * @param updateUserRequestDto
     * @return
     */
    @PutMapping("")
    public ResponseEntity<UpdateUserResponseDto> updateUser(@Valid @RequestBody UpdateUserRequestDto updateUserRequestDto,
                                                            @RequestHeader(value = "Authorization")String token) {
        try {
            long userId = authManager.extractUserId(token);
            String userNickname = updateUserRequestDto.getUserNickname();
            String userPassword = updateUserRequestDto.getUserPassword();
            UpdateUserResponseDto updateUserResponseDto = userService.updateUser(userId, userNickname, userPassword);
            return ResponseEntity.status(HttpStatus.OK).body(updateUserResponseDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 회원 프로필 수정 (현재는 description only)
     * 추후 프로필 사진 변경 기능 넣을 것
     * @param updateUserProfileRequestDto
     * @param token
     * @return
     */
    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponseDto> updateUserProfile(@RequestBody UpdateUserProfileRequestDto updateUserProfileRequestDto,
                                                                    @RequestHeader(value = "Authorization")String token) {
        try {
           long userId = authManager.extractUserId(token);
            String description = updateUserProfileRequestDto.getDescription();
            UserProfileResponseDto userProfileResponseDto = userService.updateUserProfile(userId, description);
            return ResponseEntity.status(HttpStatus.OK).body(userProfileResponseDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 회원 삭제
     * @param token
     * @return
     */
    @DeleteMapping("")
    ResponseEntity<DeleteUserResponseDto> deleteUser(@RequestHeader(value = "Authorization")String token) {
        try{
            long userId = authManager.extractUserId(token);
            DeleteUserResponseDto deleteUserResponseDto = userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(deleteUserResponseDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
