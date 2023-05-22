package com.redit.repository;

import com.redit.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SubreditRepo extends JpaRepository<Subreddit,Long> {
    Optional<Subreddit> findByName(String name);
}
