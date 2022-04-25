package com.tutoring.tutoring.repository;

import com.tutoring.tutoring.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNameAndUserPassword(String userName, String userPassword);
}
