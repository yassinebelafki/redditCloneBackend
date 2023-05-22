package com.redit.mapper;


import com.redit.dto.SubredditDto;
import com.redit.model.Post;
import com.redit.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    @Mapping(target = "subRedditName",source = "name")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subreddit);
}