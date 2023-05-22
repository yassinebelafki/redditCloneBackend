package com.redit.repository;

import com.redit.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Long> {
    Optional findByToken(String token);

    void deleteByToken(String token);
}
