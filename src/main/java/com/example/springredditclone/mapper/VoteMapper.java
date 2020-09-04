package com.example.springredditclone.mapper;

import com.example.springredditclone.dto.VoteDto;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;
import com.example.springredditclone.model.Vote;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    
    @Mapping(target = "voteId", ignore=true)
    @Mapping(target = "voteType", source = "voteDto.voteType")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Vote mapToVote(VoteDto voteDto, Post post, User user);
}
