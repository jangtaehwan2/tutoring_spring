package com.tutoring.tutoring.controller;

import com.tutoring.tutoring.domain.AuthorizationManager;
import com.tutoring.tutoring.domain.user.dto.*;
import com.tutoring.tutoring.domain.userprofile.dto.UpdateUserProfileRequestDto;
import com.tutoring.tutoring.domain.userprofile.dto.UserProfileResponseDto;
import com.tutoring.tutoring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;
    private final AuthorizationManager authorizationManager;

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
     * 회원수정 (닉네임, 패스워드)
     * 본인인지 JWT 확인하는 로직 필요
     * @param updateUserRequestDto
     * @param userId
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UpdateUserResponseDto> updateUser(@Valid @RequestBody UpdateUserRequestDto updateUserRequestDto, @PathVariable long userId,
                                                            @RequestHeader(value = "Authorization")String token) {
        try {
            if(!authorizationManager.identify(token, userId)) {
                throw new Exception("Token&Request Not Matched");
            }
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
     * @param userId
     * @param token
     * @return
     */
    @PutMapping("/{userId}/profile")
    public ResponseEntity<UserProfileResponseDto> updateUserProfile(@RequestBody UpdateUserProfileRequestDto updateUserProfileRequestDto, @PathVariable long userId,
                                                                    @RequestHeader(value = "Authorization")String token) {
        try {
            if(!authorizationManager.identify(token, userId)) {
                throw new Exception("Token&Request Not Matched");
            }
            String description = updateUserProfileRequestDto.getDescription();
            UserProfileResponseDto userProfileResponseDto = userService.updateUserProfile(userId, description);
            return ResponseEntity.status(HttpStatus.OK).body(userProfileResponseDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 회원 삭제
     * @param userId
     * @param token
     * @return
     */
    @DeleteMapping("/{userId}")
    ResponseEntity<DeleteUserResponseDto> deleteUser(@PathVariable long userId, @RequestHeader(value = "Authorization")String token) {
        try{
            if(!authorizationManager.identify(token, userId)) {
                throw new Exception("Token&Request Not Matched");
            }
            DeleteUserResponseDto deleteUserResponseDto = userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deleteUserResponseDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
