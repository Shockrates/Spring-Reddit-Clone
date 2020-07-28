package com.example.springredditclone.service;

import java.util.List;

import com.example.springredditclone.dto.SubredditDto;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.repository.SubredditRepository;
import com.example.springredditclone.exception.SubredditNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

//Import Instants
import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(toList());
	}
    @Transactional(readOnly = true)
	public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                    .orElseThrow(
                        () -> new SubredditNotFoundException("Subreddit not found with id -" + id)
                    );
        return mapToDto(subreddit);
		
	}

    @Transactional
	public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = subredditRepository.save(mapToSubreddit(subredditDto));
        subredditDto.setId(subreddit.getId());
		return subredditDto;
    }
    
    private Subreddit mapToSubreddit(SubredditDto subredditDto) {
        return Subreddit.builder()
                .name("/r/"+subredditDto.getName())
                .description(subredditDto.getDescription())
                .user(authService.getCurrentUser())
                .createdDate(now())
                .build();
                
    }
   

    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder()
                    .name(subreddit.getName())
                    .id(subreddit.getId())
                    .postCount(subreddit.getPosts().size())
                    .build();
    }

    



}
