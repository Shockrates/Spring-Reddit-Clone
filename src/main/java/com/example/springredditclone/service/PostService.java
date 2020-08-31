package com.example.springredditclone.service;

import java.util.List;

import com.example.springredditclone.dto.PostRequest;
import com.example.springredditclone.dto.PostResponse;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.model.User;
import com.example.springredditclone.exception.SubredditNotFoundException;
import com.example.springredditclone.mapper.PostMapper;
import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.SubredditRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

	private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
	private final AuthService authService;
	private final PostMapper postMapper;
	
    

	public void save(PostRequest postRequest) {

		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
			.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
		User currentUser = authService.getCurrentUser();
		postRepository.save(postMapper.map(postRequest, subreddit, currentUser)); 
	}

	public List<PostResponse> getAllPosts() {
		return null;
	}

	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() ->  new PostNotFoundException(id.toString()));
		return null;
	}

	public List<PostResponse> getPostsBySubreddit(Long id) {
		return null;
	}

	public List<PostResponse> getPostByUsername(String username) {
		return null;
	}

	

	
}
