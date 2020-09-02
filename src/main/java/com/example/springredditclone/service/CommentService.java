package com.example.springredditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.springredditclone.dto.CommentsDto;
import com.example.springredditclone.exception.PostNotFoundException;
import com.example.springredditclone.mapper.CommentMapper;
import com.example.springredditclone.model.Comment;
import com.example.springredditclone.model.NotificationEmail;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;
import com.example.springredditclone.repository.CommentRepository;
import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.UserRepository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final CommentMapper commentMapper;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;
	
	public void createComment(CommentsDto commentsDto) {
		Post post = postRepository.findById(commentsDto.getPostId())
						.orElseThrow(
							() -> new PostNotFoundException(commentsDto.getPostId().toString())
						);
		User currentUser = authService.getCurrentUser();
		Comment comment = commentMapper.map(commentsDto, post, currentUser);
		commentRepository.save(comment);
		sendCommentNotification(currentUser, post);
	}

	private void sendCommentNotification(User currentUser, Post post){
		String message = mailContentBuilder.build(currentUser.getUsername()+"  posted a comment on your post "+post.getUrl());
		NotificationEmail notificationEmail = new NotificationEmail(currentUser.getUsername() + " Commented on your post ", post.getUser().getEmail(), message);
		mailService.senMail(notificationEmail);

	}

	public List<CommentsDto> getCommentByPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(
						() -> new PostNotFoundException(postId.toString())
					);
		return commentRepository.findByPost(post)
				.stream()
				.map(commentMapper::mapToDto)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<CommentsDto> getCommentByUser(String username) {
		User user = userRepository.findByUsername(username)
			.orElseThrow(
				() -> new UsernameNotFoundException(username)
			);
		return commentRepository.findAllByUser(user)		
				.stream()
				.map(commentMapper::mapToDto)
				.collect(Collectors.toList());
	}


}


