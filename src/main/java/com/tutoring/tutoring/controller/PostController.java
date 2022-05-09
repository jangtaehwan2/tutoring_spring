package com.tutoring.tutoring.controller;

import com.tutoring.tutoring.domain.AuthManager;
import com.tutoring.tutoring.domain.post.dto.CreatePostRequestDto;
import com.tutoring.tutoring.domain.post.dto.CreatePostResponseDto;
import com.tutoring.tutoring.domain.post.dto.ReadPostDto;
import com.tutoring.tutoring.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;
    private final AuthManager authManager;

    /**
     * 글 작성하기 (추후 권한작업) 1
     * 댓글 작성하기 (추후 권한작업)
     * 글 리스트 조회하기 (PUBLIC) 1
     * 글 리스트 조회하기 (TEAM)
     * 글 상세 조회하기 (추후 권한작업)
     * 글 수정하기 (추후 권한작업)
     * 글 삭제하기 (추후 권한작업)
     */

    /**
     * 글 작성, 추후 속한 팀이 맞는지 확인하는 로직 추가 필요
     * @param token
     * @param createPostRequestDto
     * @return
     */
    @PostMapping("/team/{teamId}/post")
    public ResponseEntity<CreatePostResponseDto> createPost(@RequestHeader(name = "Authorization")String token, @RequestBody CreatePostRequestDto createPostRequestDto, @PathVariable(name = "teamId") long teamId) {
        try {
            long userId = authManager.extractUserId(token);
            String title = createPostRequestDto.getTitle();
            String description = createPostRequestDto.getDescription();
            List<String> tags = createPostRequestDto.getTags();

            CreatePostResponseDto createPostResponseDto = postService.createPost(title, tags, description, userId, teamId);
            System.out.println(createPostResponseDto.toString());

            return ResponseEntity.status(HttpStatus.OK).body(createPostResponseDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * public 팀에 속한 포스트 목록 읽기
     * @return
     */
    @GetMapping("/post")
    public ResponseEntity<List<ReadPostDto>> readPostList() {
        try {
            List<ReadPostDto> readPostDtoList = postService.readPostList();
            return ResponseEntity.status(HttpStatus.OK).body(readPostDtoList);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 특정 팀에 속한 포스트 목록 읽기
     * @param teamId
     * @return
     */
    @GetMapping("/team/{teamId}/post")
    public ResponseEntity<List<ReadPostDto>> readTeamPostList(@PathVariable(name = "teamId")long teamId) {
        try {
            List<ReadPostDto> readPostDtoList = postService.readTeamPostList(teamId);
            return ResponseEntity.status(HttpStatus.OK).body(readPostDtoList);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
