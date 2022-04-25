package com.tutoring.tutoring.domain.user;

import com.tutoring.tutoring.domain.userprofile.UserProfile;
import lombok.*;

import javax.persistence.*;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String userName;

    @NonNull
    private String userPassword;

    @NonNull
    private String userNickname;
}
