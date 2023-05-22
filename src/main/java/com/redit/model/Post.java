package com.redit.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotBlank(message = "Name can't be empty")
    private String postName;

    private String url;

    @Lob
    private String description;

    private Integer voteCount=0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private MyUser user;

    private Instant cretaedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subrId",referencedColumnName = "id")
    @ToString.Exclude
    private Subreddit subreddit;




}
