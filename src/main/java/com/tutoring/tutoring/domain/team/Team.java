package com.tutoring.tutoring.domain.team;

import com.tutoring.tutoring.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String name;

    private String tag;

    private String description;

    @NonNull
    private TeamType type; // public or private

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User host;

    @Builder
    public Team(@NonNull String name, String tag, String description, @NonNull TeamType type, @NonNull User host) {
        this.name = name;
        this.tag = tag;
        this.description = description;
        this.type = type;
        this.host = host;
    }
}
