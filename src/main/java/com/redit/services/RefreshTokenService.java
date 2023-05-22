package com.redit.services;


import com.redit.exceptions.SpingReddiExp;
import com.redit.model.RefreshToken;
import com.redit.repository.RefreshTokenRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepo;

    RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreated(Instant.now());

        return refreshTokenRepo.save(refreshToken);
    }

    void validateRefreshToken(String token) throws Throwable {
        refreshTokenRepo.findByToken(token)
                .orElseThrow(() -> new SpingReddiExp("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepo.deleteByToken(token);
    }
}