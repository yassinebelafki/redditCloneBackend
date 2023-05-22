package com.redit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private String authenticationToken;
    private String username;
    //new fields
    private String refreshToken;
    private Instant expiresAt;



}
