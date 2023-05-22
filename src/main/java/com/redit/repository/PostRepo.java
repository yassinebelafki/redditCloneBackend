package com.redit.repository;

import com.redit.model.MyUser;
import com.redit.model.Post;
import com.redit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
    List<Post> findByUser(MyUser user);
}
