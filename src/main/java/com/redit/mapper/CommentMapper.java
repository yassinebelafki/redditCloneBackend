package com.redit.mapper;


import com.redit.dto.CommentDto;
import com.redit.model.Comment;
import com.redit.model.MyUser;
import com.redit.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(target = "postId",source = "post.postId")
    @Mapping(target = "username",source = "user.username")
    CommentDto mapToCommentDto(Comment comment);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "text",source = "commentDto.text")
    @Mapping(target = "CreteatedDate",expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    Comment mapToComment(CommentDto commentDto,Post post,MyUser user);



}
