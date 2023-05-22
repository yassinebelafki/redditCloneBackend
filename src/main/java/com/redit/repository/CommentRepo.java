package com.redit.repository;

import com.redit.model.Comment;
import com.redit.model.MyUser;
import com.redit.model.Post;
import com.redit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findByUser(MyUser user);
}
