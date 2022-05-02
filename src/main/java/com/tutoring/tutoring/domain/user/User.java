package com.tutoring.tutoring.domain.user;

import com.tutoring.tutoring.domain.subscription.Subscription;
import com.tutoring.tutoring.domain.userprofile.UserProfile;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(unique = true)
    private String userName;

    @NonNull
    private String userPassword;

    @NonNull
    private String userNickname;

//    @OneToMany(mappedBy = "user")
//    private List<Subscription> subscriptions = new ArrayList<>();

    @Builder
    public User(@NonNull String userName, @NonNull String userPassword, @NonNull String userNickname) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
    }

    public void updateUser(String userNickname, String userPassword) {
        this.userNickname = userNickname;
        this.userPassword = userPassword;
    }
}
