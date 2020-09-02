package com.example.springredditclone.controller;

import java.util.List;

import com.example.springredditclone.dto.CommentsDto;
import com.example.springredditclone.service.CommentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;


@RestController 
@RequestMapping("api/comments/")
@AllArgsConstructor
public class CommentsController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto){
        commentService.createComment(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
        
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentByPost(postId));
    }
    
    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsFromUser(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentByUser(username));
    }
    
    
}