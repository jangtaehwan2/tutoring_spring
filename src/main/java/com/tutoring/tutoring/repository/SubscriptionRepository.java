package com.tutoring.tutoring.repository;

import com.tutoring.tutoring.domain.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByTeamIdAndUserId(long teamId, long userId);
    List<Subscription> findAllByUserId(long userId);
}
