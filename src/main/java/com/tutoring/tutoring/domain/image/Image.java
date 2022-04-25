package com.tutoring.tutoring.domain.image;

import com.tutoring.tutoring.domain.post.Post;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String imageName;

    @NonNull
    private String path;

    @NonNull
    private long size;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey)
    private Post post;

    @Builder
    public Image(@NonNull String imageName, @NonNull String path, @NonNull long size, @NonNull Post post) {
        this.imageName = imageName;
        this.path = path;
        this.size = size;
        this.post = post;
    }
}
