package com.tutoring.tutoring.domain.user;

import com.tutoring.tutoring.domain.userprofile.UserProfile;
import lombok.*;

import javax.persistence.*;

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

    @Builder
    public User(@NonNull String userName, @NonNull String userPassword, @NonNull String userNickname) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
    }
}
