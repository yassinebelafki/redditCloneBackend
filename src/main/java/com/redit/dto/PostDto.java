package com.redit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long postId;

    private String postName;

    private String subredditName;

    private String userName;

    private String url;

    private String description;

    private Integer voteCount;

    private Integer commentCount;

    private String duration;






}
