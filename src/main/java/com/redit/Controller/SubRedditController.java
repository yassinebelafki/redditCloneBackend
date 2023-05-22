package com.redit.Controller;


import com.redit.dto.SubredditDto;
import com.redit.exceptions.SpingReddiExp;
import com.redit.mapper.SubredditMapper;
import com.redit.services.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubRedditController {
    private final SubredditService subredditService;


    @PostMapping("/create")
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto){
        return new ResponseEntity<>(subredditService.saveSubreddit(subredditDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public  ResponseEntity<List<SubredditDto>> getAllSubreddits(){
       List<SubredditDto> subredditDtos =  subredditService.getAll();
        return new ResponseEntity<>(subredditDtos,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable("id") Long id) throws SpingReddiExp {
        SubredditDto tempSubredditDto=subredditService.getSubredditById(id);
        return new ResponseEntity<>(tempSubredditDto,HttpStatus.OK);
    }
}
