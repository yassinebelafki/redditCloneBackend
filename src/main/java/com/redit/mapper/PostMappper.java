package com.redit.mapper;


import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.redit.dto.PostDto;
import com.redit.model.MyUser;
import com.redit.model.Post;
import com.redit.model.Subreddit;
import com.redit.repository.CommentRepo;
import com.redit.services.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")
public abstract class PostMappper {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private AuthService authService;

    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    //new feilds
    @Mapping(target = "commentCount",expression = "java(commentCount(post))")
    @Mapping(target = "duration",expression = "java(getDuration(post))")
     public abstract PostDto mapToDto(Post post);

    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "cretaedTime", expression = "java(java.time.Instant.now())")
    @Mapping(target = "postName", source = "postDto.postName")
    @Mapping(target = "description", source = "postDto.description")
    @Mapping(target = "url", source = "postDto.url")
    //new feilds
    @Mapping(target = "voteCount",constant = "0")
    public abstract  Post mapToPost(PostDto postDto, Subreddit subreddit, MyUser user);

    Integer commentCount(Post post){return commentRepo.findByPost(post).size();}
    String getDuration (Post post){return TimeAgo.using(post.getCretaedTime().toEpochMilli());}

}
