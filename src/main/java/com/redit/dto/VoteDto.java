package com.redit.dto;


import com.redit.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {

    private Long postId;

    private VoteType voteType;


}
