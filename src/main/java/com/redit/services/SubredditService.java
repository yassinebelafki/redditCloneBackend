package com.redit.services;


import com.redit.dto.SubredditDto;
import com.redit.exceptions.SpingReddiExp;
import com.redit.mapper.SubredditMapper;
import com.redit.model.Subreddit;
import com.redit.repository.SubreditRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class SubredditService {

    private final SubreditRepo subreditRepo;

    private final SubredditMapper subredditMapper;


    public SubredditDto saveSubreddit(SubredditDto subredditDto){
           Subreddit subreddit= subredditMapper.mapDtoToSubreddit(subredditDto);
           subreddit = subreditRepo.save(subreddit);
           subredditDto.setId(subreddit.getId());
           return subredditDto;
    }



    public List<SubredditDto> getAll() {
        return subreditRepo.findAll().stream().map(subredditMapper::mapSubredditToDto)
                                .collect(Collectors.toList());
    }


    public SubredditDto getSubredditById(Long id) throws SpingReddiExp{
        Subreddit subreddit = subreditRepo.findById(id).orElseThrow(()->new SpingReddiExp("the sub does not exist"));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
