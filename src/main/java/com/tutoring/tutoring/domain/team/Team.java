package com.tutoring.tutoring.domain.team;

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

    @NonNull
    private String tag;

    private String description;

    @NonNull
    private String type; // public or private

    @NonNull
    @JoinColumn(name = "user_id")
    private long hostId;
}
