package com.redit.Controller;


import com.redit.dto.AuthResponse;
import com.redit.dto.LoginRequest;
import com.redit.dto.RefreshTokenDto;
import com.redit.dto.RegisterRequest;
import com.redit.exceptions.SpingReddiExp;
import com.redit.services.AuthService;
import com.redit.services.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class RegisterController {

    private final AuthService authService;


    private final RefreshTokenService refreshTokenService;

    @PostMapping("/singup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) throws SpingReddiExp {
        authService.signup(registerRequest);
        return new ResponseEntity("User regestration success", HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyUser(@PathVariable("token") String token) throws SpingReddiExp {
        authService.verifyUserToken(token);
        return new ResponseEntity<>("Account is verified",HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        AuthResponse authResponse= authService.login(loginRequest);
        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<AuthResponse> refreshTokens(@Valid @RequestBody RefreshTokenDto refreshTokenDto) throws Throwable {

        return new ResponseEntity<AuthResponse>(authService.refreshToken(refreshTokenDto),HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenDto refreshTokenDto) throws Throwable {
        refreshTokenService.deleteRefreshToken(refreshTokenDto.getRefreshToken());
        return new ResponseEntity<String>("the logout is done sucessfuly",HttpStatus.OK);
    }


}
