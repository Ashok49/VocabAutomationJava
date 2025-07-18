package com.ashokvocab.vocab_automation.repository;

import com.ashokvocab.vocab_automation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserid(String userid);
    Optional<User> findByEmail(String email);
}

