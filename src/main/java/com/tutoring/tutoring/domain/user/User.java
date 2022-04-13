package com.tutoring.tutoring.domain.user;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @Generated
    private long id;

    @NonNull
    private String userName;

    @NonNull
    private String userPassword;

    @NonNull
    private String userNickname;
}
