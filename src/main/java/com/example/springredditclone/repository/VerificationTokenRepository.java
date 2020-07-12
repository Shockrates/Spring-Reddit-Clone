package com.example.springredditclone.repository;

import java.util.Optional;

import com.example.springredditclone.model.VerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

    Optional<VerificationToken> findByToken(String token);
    
}