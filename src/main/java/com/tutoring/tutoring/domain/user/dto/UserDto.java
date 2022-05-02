package com.tutoring.tutoring.domain.user.dto;


import com.tutoring.tutoring.domain.user.User;
import com.tutoring.tutoring.domain.userprofile.UserProfile;
import com.tutoring.tutoring.domain.userprofile.dto.UserProfileDto;
import lombok.Builder;
import lombok.Getter;

/**
 * 무한 참조 또는 민감한 정보를 제외한 반환용 Dto
 */
@Getter
public class UserDto {
    private long id;
    private String userNickname;
    private UserProfileDto userProfile;

    @Builder
    public UserDto(User user, UserProfileDto userProfile) {
        this.id = user.getId();
        this.userNickname = user.getUserNickname();
        this.userProfile = userProfile;
    }
}
