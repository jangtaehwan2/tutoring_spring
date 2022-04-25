package com.tutoring.tutoring.repository;

import com.tutoring.tutoring.domain.teamprofile.TeamProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamProfileRepository extends JpaRepository<TeamProfile, Long> {
}
