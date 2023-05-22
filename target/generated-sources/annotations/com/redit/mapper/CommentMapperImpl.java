package com.redit.mapper;

import com.redit.dto.CommentDto;
import com.redit.model.Comment;
import com.redit.model.MyUser;
import com.redit.model.Post;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-11T01:49:17+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDto mapToCommentDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto commentDto = new CommentDto();

        commentDto.setPostId( commentPostPostId( comment ) );
        commentDto.setUsername( commentUserUsername( comment ) );
        commentDto.setId( comment.getId() );
        commentDto.setText( comment.getText() );
        commentDto.setCreteatedDate( comment.getCreteatedDate() );

        return commentDto;
    }

    @Override
    public Comment mapToComment(CommentDto commentDto, Post post, MyUser user) {
        if ( commentDto == null && post == null && user == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        if ( commentDto != null ) {
            comment.text( commentDto.getText() );
        }
        if ( post != null ) {
            comment.post( post );
            comment.user( post.getUser() );
        }
        comment.CreteatedDate( java.time.Instant.now() );

        return comment.build();
    }

    private Long commentPostPostId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        Post post = comment.getPost();
        if ( post == null ) {
            return null;
        }
        Long postId = post.getPostId();
        if ( postId == null ) {
            return null;
        }
        return postId;
    }

    private String commentUserUsername(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        MyUser user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }
}
