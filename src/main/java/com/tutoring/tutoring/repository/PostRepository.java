package com.tutoring.tutoring.repository;

import com.tutoring.tutoring.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("SqlResolve")
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT * FROM post p INNER JOIN team t on p.team_id = t.id where t.type = 'PUBLIC'", nativeQuery = true)
    List<Post> readPostList();

    @Query(value = "SELECT * FROM post p INNER JOIN team t on p.team_id = t.id where p.team_id = :teamId ", nativeQuery = true)
    List<Post> readTeamPostList(@Param("teamId") long teamId);
}
