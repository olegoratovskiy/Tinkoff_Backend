package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommentsResponseDto {
    List<CommentResponseDto> comments;
    PageInfoResponse pageInfo;
}
