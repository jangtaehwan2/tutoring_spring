package com.tutoring.tutoring.service;

import com.tutoring.tutoring.domain.post.Post;
import com.tutoring.tutoring.domain.post.dto.CreatePostResponseDto;
import com.tutoring.tutoring.domain.post.dto.PostDto;
import com.tutoring.tutoring.domain.post.dto.ReadPostDto;
import com.tutoring.tutoring.domain.team.Team;
import com.tutoring.tutoring.domain.team.dto.TeamDto;
import com.tutoring.tutoring.domain.user.User;
import com.tutoring.tutoring.domain.user.dto.UserDto;
import com.tutoring.tutoring.domain.userprofile.dto.UserProfileDto;
import com.tutoring.tutoring.repository.PostRepository;
import com.tutoring.tutoring.repository.TeamRepository;
import com.tutoring.tutoring.repository.UserProfileRepository;
import com.tutoring.tutoring.repository.UserRepository;
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

    public List<ReadPostDto> readPostList() {
        List<ReadPostDto> readPostDtoList = new ArrayList<>();

        List<Post> posts = postRepository.readPostList();
        for (Post post : posts) {
            ReadPostDto readPostDto = postToDto(post);
            readPostDtoList.add(readPostDto);
        }
        return readPostDtoList;
    }

    public List<ReadPostDto> readTeamPostList(long teamId) {
        List<ReadPostDto> readPostDtoList = new ArrayList<>();

        List<Post> posts = postRepository.readTeamPostList(teamId);
        for (Post post : posts) {
            ReadPostDto readPostDto = postToDto(post);
            readPostDtoList.add(readPostDto);
        }
        return readPostDtoList;
    }
}
