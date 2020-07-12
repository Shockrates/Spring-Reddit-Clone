package com.example.springredditclone.repository;

import java.util.Optional;

import com.example.springredditclone.model.Subreddit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubredditRepository extends JpaRepository<Subreddit, Long>{

    Optional<Subreddit> findByName(String subredditName);
    
}