package com.tutoring.tutoring.domain.comment;

import com.tutoring.tutoring.domain.post.Post;
import com.tutoring.tutoring.domain.user.User;
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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @NonNull
    private long time;

    @Builder
    public Comment(@NonNull String description, @NonNull User user, @NonNull Post post, @NonNull long time) {
        this.description = description;
        this.user = user;
        this.post = post;
        this.time = time;
    }
}
