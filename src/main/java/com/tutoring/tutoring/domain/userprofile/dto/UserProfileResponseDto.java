package com.tutoring.tutoring.domain.userprofile.dto;

import com.tutoring.tutoring.domain.userprofile.UserProfile;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfileResponseDto {
    private long id;

    private String fileName;

    private long fileSize;

    private String filePath;

    private String description;

    @Builder
    public UserProfileResponseDto(UserProfile userProfile) {
        this.id = userProfile.getId();
        this.fileName = userProfile.getFileName();
        this.fileSize = userProfile.getFileSize();
        this.filePath = userProfile.getFilePath();
        this.description = userProfile.getDescription();
    }
}
