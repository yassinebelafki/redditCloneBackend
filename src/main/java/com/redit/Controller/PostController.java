package com.redit.Controller;


import com.redit.dto.PostDto;
import com.redit.exceptions.ModelException;
import com.redit.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200/")

public class PostController {


    private final PostService postService;



    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody PostDto postDto) throws ModelException {
        postService.createPost(postDto);
        return new ResponseEntity<>("post is created", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPost(){

        List<PostDto> tempPosts = postService.getAllPost();
        return new ResponseEntity<>(tempPosts, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("id") Long id) throws ModelException {
        PostDto tempPost = postService.getPost(id);
        return new ResponseEntity<>(tempPost, HttpStatus.OK);
    }


    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostDto>> getAllPostBySubreddit(@PathVariable("id") Long id) throws ModelException {
        List<PostDto> tempPosts = postService.getAllPostBySubreddit(id);
        return new ResponseEntity<>(tempPosts, HttpStatus.OK);
    }


    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable("username") String username) throws ModelException {
        List<PostDto> tempPosts = postService.getAllPostByUser(username);
        return new ResponseEntity<>(tempPosts, HttpStatus.OK);
    }





}
