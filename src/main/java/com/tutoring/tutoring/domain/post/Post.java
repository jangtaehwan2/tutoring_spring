package com.tutoring.tutoring.domain.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String title;

    @NonNull
    private String tag;

    @NonNull
    private String description;

    @NonNull
    @JoinColumn(name = "user_id")
    private long userId;

    @JoinColumn(name = "team_id")
    private long teamId;
}
