package com.tutoring.tutoring.controller;

import com.tutoring.tutoring.AuthManager;
import com.tutoring.tutoring.domain.comment.dto.CommentDto;
import com.tutoring.tutoring.domain.comment.dto.CreateCommentRequestDto;
import com.tutoring.tutoring.domain.comment.dto.CreateCommentResponseDto;
import com.tutoring.tutoring.domain.post.dto.CreatePostRequestDto;
import com.tutoring.tutoring.domain.post.dto.CreatePostResponseDto;
import com.tutoring.tutoring.domain.post.dto.ReadPostDto;
import com.tutoring.tutoring.domain.post.dto.SearchPostRequestDto;
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
     * 댓글 작성하기 (추후 권한작업) 1
     * 글 리스트 조회하기 (PUBLIC) 1
     * 글 리스트 조회하기 (TEAM) 1
     * 글 상세 조회하기 (추후 권한작업) 1
     * 글 수정하기 (추후 권한작업)
     * 글 삭제하기 (추후 권한작업)
     *
     * 글 검색 기능 (title)
     * 글 검색 기능 (userId)
     * 글 검색 기능 (tag)
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

    // 특정 포스트 읽기
    @GetMapping("/team/{teamId}/post/{postId}")
    public ResponseEntity<ReadPostDto> readPost(@PathVariable(name="teamId") long teamId,
                                                @PathVariable(name="postId") long postId,
                                                @RequestHeader(name="Authorization") String token) {
        try {
            long userId = authManager.extractUserId(token);
            if (authManager.isMember(teamId, userId)) {
                ReadPostDto readPostDto = postService.readPost(postId);
                return ResponseEntity.status(HttpStatus.OK).body(readPostDto);
            } else {
                throw new Exception("userId : " + userId + "is not Member of TeamId : " + teamId);
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 특정 포스트 삭제
    @DeleteMapping("/team/{teamId}/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable(name="teamId") long teamId,
                                        @PathVariable(name="postId") long postId,
                                        @RequestHeader(name="Authorization") String token) {
        try {
            long userId = authManager.extractUserId(token);
            String response = postService.deletePost(postId, userId);
            if(response==null) {
                throw new Exception("Forbidden");
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    // 특정 포스트의 댓글 읽기
    @GetMapping("/team/{teamId}/post/{postId}/comment")
    public ResponseEntity<List<CommentDto>> readComment(@PathVariable(name="teamId") long teamId,
                                                        @PathVariable(name="postId") long postId,
                                                        @RequestHeader(name="Authorization") String token) {
        try{
            long userId = authManager.extractUserId(token);
            if (authManager.isMember(teamId, userId)) {
                List<CommentDto> commentList = postService.readPostComment(postId);
                return ResponseEntity.status(HttpStatus.OK).body(commentList);
            } else {
                throw new Exception("userId : " + userId + "is not Member of TeamId : " + teamId);
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 특정 포스트에 댓글 달기
    @PostMapping("/team/{teamId}/post/{postId}/comment")
    public ResponseEntity<CreateCommentResponseDto> createComment(@PathVariable(name="teamId") long teamId,
                                                                  @PathVariable(name="postId") long postId,
                                                                  @RequestHeader(name="Authorization") String token,
                                                                  @RequestBody CreateCommentRequestDto requestDto) {
        try {
            long userId = authManager.extractUserId(token);
            if(authManager.isMember(teamId, userId)) {
                String description = requestDto.getDescription();
                CreateCommentResponseDto comment = postService.createComment(postId, userId, description);
                return ResponseEntity.status(HttpStatus.OK).body(comment);
            } else {
                throw new Exception("userId : " + userId + "is not Member of TeamId : " + teamId);
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    // 특정 포스트에 댓글 삭제
//    @PostMapping("/team/{teamId}/post/{postId}/comment/{commentId}")

    // 특정 포스트 이름으로 검색 (public)
    @GetMapping("/post/search")
    public ResponseEntity<List<ReadPostDto>> searchPublicPost(@RequestBody SearchPostRequestDto requestDto) {
        try {
            String requirement = requestDto.getRequirement(); // 검색 조건, 컬럼
            String query = requestDto.getQuery(); // 검색어

            List<ReadPostDto> response = postService.searchPublicPost(requirement, query);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 특정 포스트 이름으로 검색 (team)
    @GetMapping("/team/{teamId}/post/search")
    public ResponseEntity<List<ReadPostDto>> searchPost(@PathVariable(name="teamId")long teamId,
                                        @RequestBody SearchPostRequestDto requestDto) {
        try {
            String requirement = requestDto.getRequirement();
            String query = requestDto.getQuery();

            List<ReadPostDto> response = postService.searchPost(teamId, requirement, query);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 내 글 리스트 보기
    @GetMapping("/user/{userId}/post")
    public ResponseEntity<List<ReadPostDto>> userPost(@PathVariable(name="userId")long userId) {
        try {
            List<ReadPostDto> response = postService.userPost(userId);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
