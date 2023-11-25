package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostsResponseDto {
    List<PostResponseDto> posts;
    PageInfoResponse pageInfo;
}
