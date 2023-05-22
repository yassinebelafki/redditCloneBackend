package com.redit.SecuritConfig;


import com.redit.exceptions.SpingReddiExp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;
import static io.jsonwebtoken.Jwts.parserBuilder;

@Service
@Slf4j
@Data
public class JwtProvider {


    private KeyStore keyStore;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationTime;

    @PostConstruct
    public void init(){
        try{
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceStrem=getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceStrem,"secret".toCharArray());
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }



    public String generateToken(Authentication authentication) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        User user=(User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(getprivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationTime)))
                .compact();
    }

    private PrivateKey getprivateKey() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        return (PrivateKey)keyStore.getKey("springblog","secret".toCharArray());
    }
    public boolean validateJwtToken(String jwt) throws SpingReddiExp {

        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        //if the code above is executed without problems it will return true
        log.info("*************the jwt is checked ");
        return true;
    }

    private PublicKey getPublicKey() throws SpingReddiExp {
        try{
            return keyStore.getCertificate("springblog") .getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpingReddiExp("Exception in retreiving public key from keystore");
        }
    }

    public String getUsernameFromJwt(String userJwt) throws SpingReddiExp {
        Claims claims = parser().setSigningKey(getPublicKey())
                        .parseClaimsJws(userJwt)
                        .getBody();
        return claims.getSubject();
    }


    public String generateTokenWithUsername(String username) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {

        return Jwts.builder()
                .setSubject(username)
                .signWith(getprivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationTime)))
                .compact();
    }
}
