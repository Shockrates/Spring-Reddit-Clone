package com.example.springredditclone.controller;

import com.example.springredditclone.dto.VoteDto;
import com.example.springredditclone.service.VoteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/api/votes/")
public class VoteController {

    private final VoteService voteService;
    
    @PostMapping
    private ResponseEntity<Void> vote(@RequestBody VoteDto voteDto){
        voteService.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);    
    }
    
}
