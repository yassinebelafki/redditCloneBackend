package com.redit.services;

import com.redit.dto.VoteDto;
import com.redit.exceptions.ModelException;
import com.redit.exceptions.SpingReddiExp;
import com.redit.model.Post;
import com.redit.model.Vote;
import com.redit.model.VoteType;
import com.redit.repository.PostRepo;
import com.redit.repository.VoteRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class VoteService {

    private final VoteRepo voteRepo;

    private final PostRepo postRepo;

    private final AuthService authService;

    public void vote(VoteDto voteDto) throws ModelException, SpingReddiExp {
        Post tempPost = postRepo.findById(voteDto.getPostId())
                .orElseThrow(()->new ModelException("this post does not exist"));
        Optional<Vote> latestVoteByPostAndUser = voteRepo.findTopByPostAndUserOrderByVoteidDesc(tempPost,authService.getCurrentUser());
        log.info("********************"+latestVoteByPostAndUser.toString());
        if (latestVoteByPostAndUser.isPresent() &&
                latestVoteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new SpingReddiExp("you can same vote for the same post 2 times !!");
        }


        if (VoteType.UPVOTE.equals(voteDto.getVoteType())){

            tempPost.setVoteCount(tempPost.getVoteCount()+1);
        }
        else {
            tempPost.setVoteCount(tempPost.getVoteCount()-1);
        }
        postRepo.save(tempPost);
        voteRepo.save(mapToVote(voteDto,tempPost));
    }

    private Vote mapToVote(VoteDto voteDto,Post post){
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
