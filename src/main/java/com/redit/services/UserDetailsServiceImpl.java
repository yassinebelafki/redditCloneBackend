package com.redit.services;

import com.redit.exceptions.SpingReddiExp;
import com.redit.model.MyUser;
import com.redit.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("**************here the authmanager call for this userdetails impl to find the user");
        Optional<MyUser> userOptional=userRepo.findByUsername(username);
        MyUser myUser=userOptional.orElseThrow(()-> new UsernameNotFoundException("user does not exist"));
        return new User(myUser.getUsername(),myUser.getPassword(), myUser.isEnabled(),
                        true,true,true,getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));

    }
}
