package com.example.springredditclone.service;

import java.util.List;

import com.example.springredditclone.dto.SubredditDto;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.repository.SubredditRepository;
import com.example.springredditclone.exception.SubredditNotFoundException;
import com.example.springredditclone.mapper.SubredditMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;

//Import Instants

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
	public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(subreddit.getId());
		return subredditDto;
    }

    @Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }
    
    @Transactional(readOnly = true)
	public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                    .orElseThrow(
                        () -> new SubredditNotFoundException("Subreddit not found with id -" + id)
                    );
        return subredditMapper.mapSubredditToDto(subreddit);	
	}

    
    

    //---------HARD CODED MAPPINGS ------------------------------//
    
    // private Subreddit mapToSubreddit(SubredditDto subredditDto) {
    //     return Subreddit.builder()
    //             .name("/r/"+subredditDto.getName())
    //             .description(subredditDto.getDescription())
    //             .user(authService.getCurrentUser())
    //             .createdDate(now())
    //             .build();
                
    // }
   

    // private SubredditDto mapToDto(Subreddit subreddit) {
    //     return SubredditDto.builder()
    //                 .name(subreddit.getName())
    //                 .id(subreddit.getId())
    //                 .postCount(subreddit.getPosts().size())
    //                 .build();
    // }

    



}
