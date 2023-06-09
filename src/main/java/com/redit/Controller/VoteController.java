package com.redit.Controller;


import com.redit.dto.VoteDto;
import com.redit.exceptions.ModelException;
import com.redit.exceptions.SpingReddiExp;
import com.redit.services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {
    private final VoteService voteService;


    @PostMapping("/create")
    public ResponseEntity<String> vote(@RequestBody VoteDto voteDto) throws ModelException, SpingReddiExp {
        voteService.vote(voteDto);
        return new ResponseEntity<>("vote submitted", HttpStatus.CREATED);
    }
}
