package com.tutoring.tutoring.domain.post.dto;

import lombok.Value;

@Value
public class SearchPostRequestDto {
    private final String query;
    private final String requirement;
}
