package com.tutoring.tutoring.domain.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreatePostRequestDto {
    private String title;
    private List<String> tags;
    private String description;
}
