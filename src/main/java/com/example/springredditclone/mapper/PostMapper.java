package com.example.springredditclone.mapper;

import com.example.springredditclone.dto.PostRequest;
import com.example.springredditclone.dto.PostResponse;
import com.example.springredditclone.model.*;
import com.example.springredditclone.repository.CommentRepository;
import com.example.springredditclone.repository.VoteRepository;
import com.example.springredditclone.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

//Is set as abstract class because we need to import information to calculate some values
@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);
    
    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "postName", source = "postName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "commentCount", expression="java(getCommentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    // @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    // @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer getCommentCount(Post post){
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post){
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post){
        return checkUsersVoteType(post, VoteType.UPVOTE);
    }

    boolean isPostDownVoted(Post post){
        return checkUsersVoteType(post, VoteType.DOWNVOTE);
    }

    private boolean checkUsersVoteType(Post post, VoteType upvote) {
        //TO DO: Make method that returns if user Upvoted or Downvoted current post
        //if (authService.isLo)
        return false;
    }

    
}