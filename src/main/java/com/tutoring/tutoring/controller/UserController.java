package com.tutoring.tutoring.controller;

import com.tutoring.tutoring.domain.user.dto.CreateUserRequestDto;
import com.tutoring.tutoring.domain.user.dto.CreateUserResponseDto;
import com.tutoring.tutoring.domain.user.dto.LoginUserRequestDto;
import com.tutoring.tutoring.domain.user.dto.LoginUserResponseDto;
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

//    @DeleteMapping("/{userId}")
//    ResponseEntity<DeleteUserResponseDto> deleteUser(@PathVariable long userId) {
//        return ResponseEntity.ok().build();
//    }


}
