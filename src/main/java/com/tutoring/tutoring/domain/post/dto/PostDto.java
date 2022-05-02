package com.tutoring.tutoring.domain.post.dto;

import com.tutoring.tutoring.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;

@Getter
public class PostDto {
    private long id;

    private String title;

    private List<String> tags;

    private String description;

    @Builder
    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.tags = Arrays.asList(post.getTag().split(","));
        this.description = post.getDescription();
    }
}
