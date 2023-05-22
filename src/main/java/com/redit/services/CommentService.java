package com.redit.services;

import com.redit.dto.CommentDto;
import com.redit.exceptions.ModelException;
import com.redit.exceptions.SpingReddiExp;
import com.redit.mapper.CommentMapper;
import com.redit.model.Comment;
import com.redit.model.MyUser;
import com.redit.model.NotificationEmail;
import com.redit.model.Post;
import com.redit.repository.CommentRepo;
import com.redit.repository.PostRepo;
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
public class CommentService {

    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;



    public void saveComment(CommentDto commentDto) throws ModelException, SpingReddiExp {

        Post tempPost=postRepo.findById(commentDto.getPostId()).orElseThrow(()->new ModelException("post does not exist"));
        MyUser user = authService.getCurrentUser();
        //log.info("******************************"+tempPost.toString());
        Comment comment=commentMapper.mapToComment(commentDto,tempPost,user);
        comment.setUser(user);
        log.info("********** saving the comment"+comment.toString());
        commentRepo.save(comment);
        String message=mailContentBuilder.build(user.getUsername() +"just commented on you Post "+tempPost.getUrl());
        mailService.sendMail(new NotificationEmail("New Comment",tempPost.getUser().getEmail(),message));


    }

    public List<CommentDto> getAllCommentForPost(Long id) throws ModelException {
        Post post=postRepo.findById(id).orElseThrow(()->new ModelException("post not founded"));
        List<CommentDto> comments=commentRepo.findByPost(post).stream().map(commentMapper::mapToCommentDto).collect(Collectors.toList());
        return comments;
    }

    public List<CommentDto> getAllCommentForUser(String username) throws ModelException {
            MyUser user = userRepo.findByUsername(username).orElseThrow(()->new ModelException("user not founded"));
        List<CommentDto> comments=commentRepo.findByUser(user).stream().map(commentMapper::mapToCommentDto).collect(Collectors.toList());
        return comments;


    }
}
