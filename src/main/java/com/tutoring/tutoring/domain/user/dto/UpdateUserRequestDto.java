package com.tutoring.tutoring.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UpdateUserRequestDto {
    @Size(min = 1, max = 20)
    private String userNickname;
    @NotBlank
    private String userPassword;
}
