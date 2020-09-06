package com.example.springredditclone.service;

import java.util.Optional;

import com.example.springredditclone.dto.VoteDto;
import com.example.springredditclone.exception.PostNotFoundException;
import com.example.springredditclone.exception.SpringRedditException;
import com.example.springredditclone.mapper.VoteMapper;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;
import com.example.springredditclone.model.Vote;
import com.example.springredditclone.model.VoteType;
import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.VoteRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class VoteService {

    private final PostRepository postRepository;
    private final AuthService authService;
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;

	public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
						.orElseThrow(
							() -> new PostNotFoundException(voteDto.getPostId().toString())
						);
        User currentUser = authService.getCurrentUser();

        //We are retrieving the recent Vote submitted by the currently logged-in user for the given Post. 
        //We are doing this using the method – findTopByPostAndUserOrderByVoteIdDesc()
        Optional<Vote> voteByPostAnduser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, currentUser);

        if (voteByPostAnduser.isPresent() && voteByPostAnduser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have  already "+voteDto.getVoteType()+"'d this post");
        }
        if (VoteType.UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount()+1);
        } else {
            post.setVoteCount(post.getVoteCount()-1);
        }
        Vote vote = voteMapper.mapToVote(voteDto, post, currentUser);
        voteRepository.save(vote);
        postRepository.save(post);
	}

}
