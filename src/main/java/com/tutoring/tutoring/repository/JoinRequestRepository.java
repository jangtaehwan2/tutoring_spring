package com.tutoring.tutoring.repository;

import com.tutoring.tutoring.domain.joinrequest.JoinRequest;
import com.tutoring.tutoring.domain.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    Optional<JoinRequest> findByTeamIdAndUserId(long teamId, long userId);
    List<JoinRequest> findByTeamId(long teamId);
}
