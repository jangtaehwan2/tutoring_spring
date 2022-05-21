package com.tutoring.tutoring.domain.team;

import com.tutoring.tutoring.domain.user.User;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull @Column(unique = true)
    private String name;

    private String tag;

    private String description;

    private String fileName;

    private long fileSize;

    private String filePath;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean isClosed;

    @NonNull
    @Column(columnDefinition = "varchar(8)")
    private String type; // public or private

    @NonNull
    @ManyToOne
    @JoinColumn(name = "host_id")
    private User host;

    @Builder
    public Team(@NonNull String name, String tag, @NonNull String type, @NonNull User host) {
        this.name = name;
        this.tag = tag;
        this.type = type;
        this.host = host;
    }

    public void update(String description, boolean isClosed) {
        this.description = description;
        this.isClosed = isClosed;
    }
}
