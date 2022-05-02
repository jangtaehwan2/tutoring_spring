package com.tutoring.tutoring.domain.post;

import com.tutoring.tutoring.domain.comment.Comment;
import com.tutoring.tutoring.domain.team.Team;
import com.tutoring.tutoring.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//    private List<Comment> comments = new ArrayList();

    @Builder
    public Post(@NonNull String title, @NonNull String tag, @NonNull String description, @NonNull User user, @NonNull Team team) {
        this.title = title;
        this.tag = tag;
        this.description = description;
        this.user = user;
        this.team = team;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user.toString() +
                ", team=" + team.toString() +
                '}';
    }
}
