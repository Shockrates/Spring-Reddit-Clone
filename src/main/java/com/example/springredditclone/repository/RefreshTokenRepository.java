package com.example.springredditclone.repository;

import java.util.Optional;

import com.example.springredditclone.model.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

    Optional<RefreshToken> findByToken(String token);
    Void deleteByToken(String token);
    
}
