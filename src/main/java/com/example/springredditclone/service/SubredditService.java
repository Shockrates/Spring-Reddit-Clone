package com.example.springredditclone.service;

import java.util.List;

import com.example.springredditclone.dto.SubredditDto;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.repository.SubredditRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final AuthService authservice;

    @Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(toList());
	}

	public SubredditDto getSubreddit(Long id) {
		return null;
	}

	public SubredditDto save(SubredditDto subredditDto) {
		return null;
    }
    
    private SubredditDto mapToDto(Subreddit subreddit){
        return SubredditDto.builder()
                    .name(subreddit.getName())
                    .id(subreddit.getId())
                    .postCount(subreddit.getPosts().size())
                    .build();
    }

}
