package com.redit.repository;

import com.redit.model.MyUser;
import com.redit.model.Post;
import com.redit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepo extends JpaRepository<Vote,Long>
{
    Optional<Vote> findTopByPostAndUserOrderByVoteidDesc(Post tempPost, MyUser currentUser);
}
