package com.tutoring.tutoring.domain.joinrequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class JoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    @JoinColumn(name = "team_id")
    private long teamId;

    @JoinColumn(name = "user_id")
    private long userId;
}
