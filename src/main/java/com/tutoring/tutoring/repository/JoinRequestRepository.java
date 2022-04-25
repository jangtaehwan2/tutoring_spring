package com.tutoring.tutoring.repository;

import com.tutoring.tutoring.domain.joinrequest.JoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
}
