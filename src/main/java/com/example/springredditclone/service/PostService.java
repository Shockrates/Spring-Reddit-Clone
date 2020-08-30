package com.example.springredditclone.service;

import java.util.List;

import com.example.springredditclone.dto.PostRequest;
import com.example.springredditclone.dto.PostResponse;
import com.example.springredditclone.model.User;
import com.example.springredditclone.exception.SubredditNotFoundException;
import com.example.springredditclone.repository.SubredditRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    

	public void save(PostRequest postRequest) {

        subredditRepository.findByName(postRequest.getSubredditName())
            .orElseThrow(()-> new SubredditNotFoundException(postRequest.getSubredditName()));
        User currentUser = authService.getCurrentUser();
	}

	public List<PostResponse> getAllPosts() {
		return null;
	}

	public PostResponse getPost(Long id) {
		return null;
	}

	public List<PostResponse> getPostsBySubreddit(Long id) {
		return null;
	}

	public List<PostResponse> getPostByUsername(String username) {
		return null;
	}

	

	
}
