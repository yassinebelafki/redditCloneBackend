package com.redit.repository;

import com.redit.model.VerificationToken;
import com.redit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByToken(String token);
}
