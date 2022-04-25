package com.tutoring.tutoring.repository;

import com.tutoring.tutoring.domain.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
