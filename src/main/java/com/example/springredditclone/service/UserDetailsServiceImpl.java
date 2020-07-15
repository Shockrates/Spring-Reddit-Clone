package com.example.springredditclone.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import com.example.springredditclone.model.User;
import com.example.springredditclone.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(
            () -> new UsernameNotFoundException("No user found with the usename: "+username)
        );
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),user.isEnabled()
        ,true,true,true,getAuhoroties("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuhoroties(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    
    
}