package com.example.springredditclone.service;

import java.util.List;

import com.example.springredditclone.dto.PostRequest;
import com.example.springredditclone.dto.PostResponse;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.model.User;
import com.example.springredditclone.exception.PostNotFoundException;
import com.example.springredditclone.exception.SubredditNotFoundException;
import com.example.springredditclone.mapper.PostMapper;
import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.SubredditRepository;
import com.example.springredditclone.repository.UserRepository;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;


@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PostService {

	
	private final PostMapper postMapper;
	private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
	private final AuthService authService;
	private final UserRepository userRepository;
	
	
    

	
	public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(
					() -> new SubredditNotFoundException(postRequest.getSubredditName())
				);
		User currentUser = authService.getCurrentUser();
		Post post = postMapper.map(postRequest, subreddit, currentUser);
		post.setVoteCount(0);
        postRepository.save(post);
    }

	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
	}

	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(
				() ->  new PostNotFoundException(id.toString())
			);
		return postMapper.mapToDto(post);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
			.orElseThrow(
				() -> new SubredditNotFoundException(id.toString())
			);
		List<Post> posts = postRepository.findAllBySubreddit(subreddit); 
		return posts
				.stream()
				.map(postMapper::mapToDto)
				.collect(toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostByUsername(String username) {
		log.info("USERNAME PASSED: "+ username);
		User user = userRepository.findByUsername(username)
			.orElseThrow(
				() -> new UsernameNotFoundException(username)
			);
		List<Post> posts = postRepository.findByUser(user);
		return posts
			.stream()
			.map(postMapper::mapToDto)
			.collect(toList());
	}

	

	
}
