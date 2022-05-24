package com.tutoring.tutoring.service;

import com.tutoring.tutoring.domain.comment.Comment;
import com.tutoring.tutoring.domain.comment.dto.CommentDto;
import com.tutoring.tutoring.domain.comment.dto.CreateCommentResponseDto;
import com.tutoring.tutoring.domain.post.Post;
import com.tutoring.tutoring.domain.post.dto.CreatePostResponseDto;
import com.tutoring.tutoring.domain.post.dto.PostDto;
import com.tutoring.tutoring.domain.post.dto.ReadPostDto;
import com.tutoring.tutoring.domain.team.Team;
import com.tutoring.tutoring.domain.team.dto.TeamDto;
import com.tutoring.tutoring.domain.user.User;
import com.tutoring.tutoring.domain.user.dto.UserDto;
import com.tutoring.tutoring.domain.userprofile.UserProfile;
import com.tutoring.tutoring.domain.userprofile.dto.UserProfileDto;
import com.tutoring.tutoring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final UserProfileRepository userProfileRepository;
    private final CommentRepository commentRepository;

    private String tagListToString(List<String> tags) {
        String tag = "";
        for (String s : tags) {
            tag += s + ",";
        }
        return tag;
    }

    private List<String> tagStringToList(String tag) {
        String[] t = tag.split(",");
        return Arrays.asList(t);
    }

    private ReadPostDto postToDto(Post post) {
        TeamDto teamDto = TeamDto.builder()
                .team(post.getTeam())
                .build();
        UserDto userDto = UserDto.builder()
                .user(post.getUser())
                .userProfile(UserProfileDto.builder()
                        .userProfile(userProfileRepository.findByUserId(post.getUser().getId()).get())
                        .build())
                .build();
        PostDto postDto = PostDto.builder()
                .post(post)
                .build();

        ReadPostDto readPostDto = ReadPostDto.builder()
                .post(postDto)
                .team(teamDto)
                .user(userDto)
                .build();

        return readPostDto;
    }

    /**
     * 글 작성 로직
     *
     * @param title
     * @param tags
     * @param description
     * @param userId
     * @param teamId
     * @return
     */
    public CreatePostResponseDto createPost(String title, List<String> tags, String description, long userId, long teamId) {
        User user = userRepository.findById(userId).get();
        Team team = teamRepository.findById(teamId).get();
        String tag = tagListToString(tags);

        Post post = Post.builder()
                .title(title)
                .tag(tag)
                .description(description)
                .user(user)
                .team(team)
                .build();

        Post savedPost = postRepository.save(post);
        System.out.println(savedPost.toString());

        UserProfileDto userProfileDto = UserProfileDto.builder()
                .userProfile(userProfileRepository.findByUserId(savedPost.getUser().getId()).get())
                .build();

        UserDto userDto = UserDto.builder()
                .user(savedPost.getUser())
                .userProfile(userProfileDto)
                .build();

        TeamDto teamDto = TeamDto.builder()
                .team(savedPost.getTeam())
                .build();


        return CreatePostResponseDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .tags(tagStringToList(savedPost.getTag()))
                .user(userDto)
                .team(teamDto)
                .build();
    }

    /**
     * public 팀의 게시글 읽기 로직
     * @return
     */
    public List<ReadPostDto> readPostList() {
        List<ReadPostDto> readPostDtoList = new ArrayList<>();

        List<Post> posts = postRepository.readPostList();
        for (Post post : posts) {
            ReadPostDto readPostDto = postToDto(post);
            readPostDtoList.add(readPostDto);
        }
        return readPostDtoList;
    }

    /**
     * 특정 팀의 게시글 읽기 로직
     * @param teamId
     * @return
     */
    public List<ReadPostDto> readTeamPostList(long teamId) {
        List<ReadPostDto> readPostDtoList = new ArrayList<>();

        List<Post> posts = postRepository.readTeamPostList(teamId);
        for (Post post : posts) {
            ReadPostDto readPostDto = postToDto(post);
            readPostDtoList.add(readPostDto);
        }
        return readPostDtoList;
    }

    /**
     * 특정 게시글 읽기 로직
     * @param postId
     * @return
     */
    public ReadPostDto readPost(long postId) {
        Post post = postRepository.findById(postId).get();
        User user = userRepository.findById(post.getUser().getId()).get();
        UserProfile userProfile = userProfileRepository.findByUserId(user.getId()).get();
        UserDto userDto = UserDto.builder()
                .user(user)
                .userProfile(UserProfileDto.builder()
                        .userProfile(userProfile)
                        .build())
                .build();

        ReadPostDto readPostDto = ReadPostDto.builder()
                .post(PostDto.builder()
                        .post(post)
                        .build())
                .team(new TeamDto(post.getTeam()))
                .user(userDto)
                .build();
        return readPostDto;
    }

    /**
     * 글에 속한 댓글 읽기 로직
     * @param postId
     * @return
     */
     public List<CommentDto> readPostComment(long postId) {
         // Post 에 속하는 코멘트 찾기
         List<Comment> CommentList = commentRepository.findAllByPostId(postId);
         // Response 를 위한 리스트 생성
         List<CommentDto> commentDtoList = new ArrayList<>();
         // 코멘트들의 형태 변경
         for (Comment comment : CommentList) {
             User user = comment.getUser();
             UserProfile userProfile = userProfileRepository.findByUserId(user.getId()).get();
             UserDto userDto = UserDto.builder()
                                .user(user)
                                .userProfile(UserProfileDto.builder()
                                        .userProfile(userProfile)
                                        .build())
                                .build();
             // 리스트에 추가
             commentDtoList.add(CommentDto.builder()
                     .id(comment.getId())
                     .description(comment.getDescription())
                     .user(userDto)
                     .build());
         }
         return commentDtoList;
     }

    /**
     * 댓글 생성 로직
     * @param postId
     * @param userId
     * @param description
     * @return
     */
     public CreateCommentResponseDto createComment(long postId, long userId, String description) {
        Comment comment = Comment.builder()
                .post(postRepository.getById(postId))
                .user(userRepository.getById(userId))
                .description(description)
                .build();
         Comment savedComment = commentRepository.save(comment);
         return CreateCommentResponseDto.builder()
                 .id(savedComment.getId())
                 .description(savedComment.getDescription())
                 .build();
     }

     public List<ReadPostDto> searchPublicPost(String requirement, String query) {
         List<ReadPostDto> responseList = new ArrayList<>();
         if(requirement.equals("title")) {
             List<Post> posts = postRepository.searchPublicPostByTitle(query);
             for(Post post : posts) {
                 responseList.add(postToDto(post));
             }
             return responseList;
         } else if (requirement.equals("tag")) {
             List<Post> posts = postRepository.searchPublicPostByTag(query);
             for(Post post : posts) {
                 responseList.add(postToDto(post));
             }
             return responseList;
         } else if (requirement.equals("description")) {
             List<Post> posts = postRepository.searchPublicPostByDescription(query);
             for(Post post : posts) {
                 responseList.add(postToDto(post));
             }
             return responseList;
         } else {
             return null;
         }
     }

    public List<ReadPostDto> searchPost(long teamId, String requirement, String query) {
        List<ReadPostDto> responseList = new ArrayList<>();
        if(requirement.equals("title")) {
            List<Post> posts = postRepository.searchPostByTitle(teamId, query);
            for(Post post : posts) {
                responseList.add(postToDto(post));
            }
            return responseList;
        } else if (requirement.equals("tag")) {
            List<Post> posts = postRepository.searchPostByTag(teamId, query);
            for(Post post : posts) {
                responseList.add(postToDto(post));
            }
            return responseList;
        } else if (requirement.equals("description")) {
            List<Post> posts = postRepository.searchPostByDescription(teamId, query);
            for(Post post : posts) {
                responseList.add(postToDto(post));
            }
            return responseList;
        } else {
            return null;
        }
    }

    public List<ReadPostDto> userPost(long userId) {
        List<ReadPostDto> responseList = new ArrayList<>();
        List<Post> posts = postRepository.searchUserPost(userId);
        for(Post post : posts) {
            responseList.add(postToDto(post));
        }
        return responseList;
    }

    public String deletePost(long postId, long userId) {
         Post post = postRepository.findById(postId).get();
         if(post.getUser().getId() == userId) {
             postRepository.delete(post);
             return "Post " + postId + " Deleted";
         } else {
             return null;
         }
    }
}
