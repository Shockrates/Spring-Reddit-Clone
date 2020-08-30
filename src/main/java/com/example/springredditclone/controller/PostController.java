package com.example.springredditclone.controller;

import java.util.List;

import com.example.springredditclone.dto.PostRequest;
import com.example.springredditclone.dto.PostResponse;
import com.example.springredditclone.service.PostService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.ResponseEntity.status;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/")
    public List<PostResponse> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @GetMapping("by-subreddit/{id}")
    public List<PostResponse> getPostBySubreddit(Long id){
        return postService.getPostsBySubreddit(id);
    }

    @GetMapping("by-user/{name}")
    public List<PostResponse> getPostByUsername(String username){
        return postService.getPostByUsername(username);
    }
    



    
}