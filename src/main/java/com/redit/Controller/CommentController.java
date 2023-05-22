package com.redit.Controller;


import com.redit.dto.CommentDto;
import com.redit.exceptions.ModelException;
import com.redit.exceptions.SpingReddiExp;
import com.redit.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<String> createComment(@RequestBody CommentDto commentDto) throws ModelException, SpingReddiExp {
            commentService.saveComment(commentDto);
            return new ResponseEntity<>("comment saved!", HttpStatus.CREATED);
    }

    @GetMapping("/all/post_id/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentForPost(@PathVariable("id") Long id) throws ModelException {
            return new ResponseEntity<>(commentService.getAllCommentForPost(id),HttpStatus.OK);
    }

    @GetMapping("/all/username/{username}")
    public ResponseEntity<List<CommentDto>> getAllCommentForPost(@PathVariable("username") String username) throws ModelException {
        return new ResponseEntity<List<CommentDto>>(commentService.getAllCommentForUser(username),HttpStatus.OK);
    }
}
