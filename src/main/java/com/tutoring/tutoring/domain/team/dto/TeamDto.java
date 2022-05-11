package com.tutoring.tutoring.domain.team.dto;

import com.tutoring.tutoring.domain.team.Team;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 무한 참조 또는 민감한 정보를 제외한 반환용 Dto
 */
@Getter
public class TeamDto {
    private long id;

    private String name;

    private List<String> tags;

    private String description;

    private String fileName;

    private long fileSize;

    private String filePath;

    private String type;

    private long hostId;

    private boolean isClosed;

    @Builder
    public TeamDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.tags = Arrays.asList(team.getTag().split(","));
        this.description = team.getDescription();
        this.fileName = team.getFileName();
        this.fileSize = team.getFileSize();
        this.filePath = team.getFilePath();
        this.type = team.getType();
        this.hostId = team.getHost().getId();
        this.isClosed = team.isClosed();
    }
}
