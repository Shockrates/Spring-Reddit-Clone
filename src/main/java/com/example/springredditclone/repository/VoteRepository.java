package com.example.springredditclone.repository;

import java.util.Optional;

import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;
import com.example.springredditclone.model.Vote;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long>{
    
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
    
