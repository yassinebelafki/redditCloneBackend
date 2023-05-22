package com.redit.dto;

import com.redit.model.MyUser;
import com.redit.model.Post;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    private String text;

    private Long postId;

    private Instant CreteatedDate;

    private String username;
}
