package com.tutoring.tutoring.repository;

import com.tutoring.tutoring.domain.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("SqlResolve")
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByIsClosedFalse();

    @Query(value = "SELECT * FROM team t where tag like concat('%', :query, '%') and is_closed = 0; ", nativeQuery = true)
    List<Team> searchTeamByTag(@Param("query")String query);

    @Query(value = "SELECT * FROM team t where name like concat('%', :query, '%') and is_closed = 0; ", nativeQuery = true)
    List<Team> searchTeamByName(@Param("query")String query);
}
