package com.tutoring.tutoring.domain.comment;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String description;

    @NonNull
    @JoinColumn(name = "post_id")
    private long postId;

    @NonNull
    @JoinColumn(name = "user_id")
    private long userId;

    @NonNull
    private long time;
}
