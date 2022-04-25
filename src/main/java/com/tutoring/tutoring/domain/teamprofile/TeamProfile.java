package com.tutoring.tutoring.domain.teamprofile;

import com.tutoring.tutoring.domain.team.Team;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TeamProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    private long fileSize;

    private String filePath;

    private String description;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean isOpen;

    @OneToOne
    @JoinColumn(name = "team_id", foreignKey = @ForeignKey(name = "profile_team"))
    private Team team;

    @Builder
    public TeamProfile(String description, Team team) {
        this.description = description;
        this.team = team;
    }
}
