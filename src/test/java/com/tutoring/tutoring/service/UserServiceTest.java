package com.tutoring.tutoring.service;

import com.tutoring.tutoring.domain.user.dto.CreateUserResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("중복 아이디 테스트")
    public void createUserTest(){
        String newUserName1 = "TEST01";
        String newUserNickname1 = "TEST01";
        String newUserPassword1 = "TEST01";

        String newUserName2 = "TEST01";
        String newUserNickname2 = "TEST02";

        userService.createUser(newUserName1, newUserNickname1, newUserPassword1);
//        userService.createUser(newUserName2, newUserNickname2, newUserPassword1);
        Assertions.assertThrows(ConstraintViolationException.class, () -> userService.createUser(newUserName2, newUserNickname2, newUserPassword1));
    }
}
