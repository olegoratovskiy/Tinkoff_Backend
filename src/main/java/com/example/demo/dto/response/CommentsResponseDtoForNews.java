package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CommentsResponseDtoForNews {
    List<CommentResponseForNewsDto> comments;
    PageInfoResponse pageInfo;

}
