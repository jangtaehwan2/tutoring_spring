package com.tutoring.tutoring.domain.userprofile;

import com.tutoring.tutoring.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    private long fileSize;

    private String filePath;

    private String description;

    @NonNull
    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_userprofile"))
    private User user;

    @Builder
    public UserProfile(String fileName, long fileSize, String filePath, String description, @NonNull User user) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.description = description;
        this.user = user;
    }

    public void updateDescription(String description) {
        this.description = description;
    }
}
