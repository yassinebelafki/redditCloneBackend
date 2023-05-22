package com.redit.mapper;

import com.redit.dto.SubredditDto;
import com.redit.model.Subreddit;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-11T01:49:17+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class SubredditMapperImpl implements SubredditMapper {

    @Override
    public SubredditDto mapSubredditToDto(Subreddit subreddit) {
        if ( subreddit == null ) {
            return null;
        }

        SubredditDto.SubredditDtoBuilder subredditDto = SubredditDto.builder();

        subredditDto.subRedditName( subreddit.getName() );
        subredditDto.id( subreddit.getId() );
        subredditDto.description( subreddit.getDescription() );

        subredditDto.numberOfPosts( mapPosts(subreddit.getPosts()) );

        return subredditDto.build();
    }

    @Override
    public Subreddit mapDtoToSubreddit(SubredditDto subreddit) {
        if ( subreddit == null ) {
            return null;
        }

        Subreddit.SubredditBuilder subreddit1 = Subreddit.builder();

        subreddit1.name( subreddit.getSubRedditName() );
        subreddit1.id( subreddit.getId() );
        subreddit1.description( subreddit.getDescription() );

        return subreddit1.build();
    }
}
