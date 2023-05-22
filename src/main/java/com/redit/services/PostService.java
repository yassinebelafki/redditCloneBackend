package com.redit.services;


import com.redit.dto.PostDto;
import com.redit.exceptions.ModelException;
import com.redit.mapper.PostMappper;
import com.redit.model.MyUser;
import com.redit.model.Post;
import com.redit.model.Subreddit;
import com.redit.repository.PostRepo;
import com.redit.repository.SubreditRepo;
import com.redit.repository.UserRepo;
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
public class PostService {

    private final PostRepo postRepo;

    private final SubreditRepo subreditRepo;

    private final AuthService authService;

    private final PostMappper postMappper;

    private final UserRepo userRepo;

    public void createPost(PostDto postDto) throws ModelException {
        MyUser currentUser=authService.getCurrentUser();
        log.info(postDto.getSubredditName()+"********************************************");
        Subreddit currentSubreddit = subreditRepo.findByName(postDto.getSubredditName()).orElseThrow(()->new ModelException("SUBRREDIT DOES NOT EXIST"));
        log.info("******************************subreddit fountded");
        log.info(currentSubreddit.toString());
        log.info(currentUser.toString());

        Post post = postMappper.mapToPost(postDto,currentSubreddit,currentUser);
        log.info(post.toString());
        postRepo.save(post);
    }
    public PostDto getPost(Long id) throws ModelException {
        return postMappper.mapToDto(postRepo.findById(id).orElseThrow(()->new ModelException("post with id"+id.toString()+" is not found"))) ;
    }


    public List<PostDto> getAllPostBySubreddit(Long id) throws ModelException {
        Subreddit tempSub= subreditRepo.findById(id).orElseThrow(()->new ModelException("Subreddit with id"+id.toString()+" is not found"));
        List<Post> posts = tempSub.getPosts();
        return  posts.stream().map(postMappper::mapToDto).collect(Collectors.toList());
    }

    public List<PostDto> getAllPostByUser(String username) throws ModelException {

        MyUser user = userRepo.findByUsername(username).orElseThrow(()->new ModelException("User with username"+username+" is not found"));
        List<Post> posts = postRepo.findByUser(user);
        return posts.stream().map(postMappper::mapToDto).collect(Collectors.toList());

    }

    public List<PostDto> getAllPost() {
        return postRepo.findAll().stream().map(postMappper::mapToDto).collect(Collectors.toList());
    }
}
