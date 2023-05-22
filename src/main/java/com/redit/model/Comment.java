package com.redit.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.apache.catalina.User;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "username is required")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId",referencedColumnName = "postId")
    private Post post;

    private Instant CreteatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private MyUser user;

}
