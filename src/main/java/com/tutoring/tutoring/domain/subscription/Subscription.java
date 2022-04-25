package com.tutoring.tutoring.domain.subscription;

import com.tutoring.tutoring.domain.team.Team;
import com.tutoring.tutoring.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "tinyint(1) default false") // 알림 설정 default false
    private boolean notification;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public Subscription(boolean notification, @NonNull User user, @NonNull Team team) {
        this.notification = notification;
        this.user = user;
        this.team = team;
    }
}
