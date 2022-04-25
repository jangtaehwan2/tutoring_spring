package com.tutoring.tutoring.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginUserRequestDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String userPassword;
}
