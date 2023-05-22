package com.redit.mapper;

import com.redit.dto.PostDto;
import com.redit.model.MyUser;
import com.redit.model.Post;
import com.redit.model.Subreddit;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-11T01:49:17+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class PostMappperImpl extends PostMappper {

    @Override
    public PostDto mapToDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDto postDto = new PostDto();

        postDto.setSubredditName( postSubredditName( post ) );
        postDto.setUserName( postUserUsername( post ) );
        postDto.setPostId( post.getPostId() );
        postDto.setPostName( post.getPostName() );
        postDto.setUrl( post.getUrl() );
        postDto.setDescription( post.getDescription() );
        postDto.setVoteCount( post.getVoteCount() );

        postDto.setCommentCount( commentCount(post) );
        postDto.setDuration( getDuration(post) );

        return postDto;
    }

    @Override
    public Post mapToPost(PostDto postDto, Subreddit subreddit, MyUser user) {
        if ( postDto == null && subreddit == null && user == null ) {
            return null;
        }

        Post.PostBuilder post = Post.builder();

        if ( postDto != null ) {
            post.postName( postDto.getPostName() );
            post.description( postDto.getDescription() );
            post.url( postDto.getUrl() );
        }
        post.subreddit( subreddit );
        post.user( user );
        post.cretaedTime( java.time.Instant.now() );
        post.voteCount( 0 );

        return post.build();
    }

    private String postSubredditName(Post post) {
        if ( post == null ) {
            return null;
        }
        Subreddit subreddit = post.getSubreddit();
        if ( subreddit == null ) {
            return null;
        }
        String name = subreddit.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String postUserUsername(Post post) {
        if ( post == null ) {
            return null;
        }
        MyUser user = post.getUser();
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
