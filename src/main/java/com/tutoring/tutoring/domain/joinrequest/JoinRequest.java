package com.tutoring.tutoring.domain.joinrequest;

import com.tutoring.tutoring.domain.team.Team;
import com.tutoring.tutoring.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class JoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public JoinRequest(String description, @NonNull Team team, @NonNull User user) {
        this.description = description;
        this.team = team;
        this.user = user;
    }
}
