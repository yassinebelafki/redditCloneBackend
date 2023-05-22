package com.redit.services;

import com.redit.dto.AuthResponse;
import com.redit.dto.LoginRequest;
import com.redit.dto.RefreshTokenDto;
import com.redit.dto.RegisterRequest;
import com.redit.SecuritConfig.JwtProvider;
import com.redit.exceptions.SpingReddiExp;
import com.redit.model.MyUser;
import com.redit.model.NotificationEmail;
import com.redit.model.VerificationToken;
import com.redit.repository.UserRepo;
import com.redit.repository.VerificationTokenRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

private final PasswordEncoder passwordEncoder;

private final UserRepo userRepo;

private final VerificationTokenRepo verificationTokenRepo;

private final MailService mailService;

private final AuthenticationManager authenticationManager;

private final JwtProvider jwtProvider;

private final MailContentBuilder mailContentBuilder;

private final RefreshTokenService refreshTokenService;

@Transactional
public void signup(RegisterRequest registerRequest) throws SpingReddiExp {
    MyUser user=new MyUser();
    user.setUsername(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setCreatedDate(Instant.now());
    user.setEnabled(false);

    //saving the user
    userRepo.save(user);
    log.info("********** user saved");
    //generate verification token for the user and store into the db

     String tokenVerification= geneateVerificationToken(user);

    log.info("********** token generated");
    String message=mailContentBuilder.build("To Activate you account please click on this link http://localhost:8080/api/auth/accountVerification/"+tokenVerification);
     mailService.sendMail(new NotificationEmail("Activation Accout",user.getEmail(),message));
}

private String geneateVerificationToken(MyUser user){
    String tokenVerification= UUID.randomUUID().toString();
    VerificationToken token= new VerificationToken();
    token.setToken(tokenVerification);
    token.setUser(user);
    verificationTokenRepo.save(token);
    return tokenVerification;
}


    public void verifyUserToken(String token) throws SpingReddiExp {
    Optional<VerificationToken> verifToken= Optional.ofNullable(verificationTokenRepo.findByToken(token).orElseThrow(() -> new SpingReddiExp("this token does not exist")));
    fetchUserAndEnable(verifToken.get());
}

    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken) throws SpingReddiExp {
        String username = verificationToken.getUser().getUsername();
        MyUser user=userRepo.findByUsername(username).orElseThrow(()->new SpingReddiExp("user does not exist"));
        log.info("*************** user fetch to enable is founded");
        user.setEnabled(true);
        userRepo.save(user);

    }

    public AuthResponse login(LoginRequest loginRequest) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        log.info("******we called for the authenticate methode in authmanager");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("*******we stored authentication of the user in secritycontex and now we call for jwtprovider");
        String token=jwtProvider.generateToken(authentication);
        return AuthResponse.builder().authenticationToken(token)
                                .username(loginRequest.getUsername())
                                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationTime()))
                                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                                .build();
    }



    public MyUser getCurrentUser() {
    Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null){
            return null;
        }
        User user=(User) authentication.getPrincipal();
        MyUser myUser = userRepo.findByUsername(user.getUsername()).orElseThrow(()->new UsernameNotFoundException("user not found"));
        return myUser;
    }

    public AuthResponse refreshToken(RefreshTokenDto refreshTokenDto) throws Throwable {
        refreshTokenService.validateRefreshToken(refreshTokenDto.getRefreshToken());
        String token= jwtProvider.generateTokenWithUsername(refreshTokenDto.getUsername());
        return AuthResponse.builder()
                            .authenticationToken(token)
                            .refreshToken(refreshTokenDto.getRefreshToken())
                            .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationTime()))
                            .username(refreshTokenDto.getUsername())
                            .build();
    }
}
