package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponseForNewsDto {
    Long id;
    String content;
    UserAccountResponseDto author;
    NewsResponseDto news;
    LocalDateTime createdAt;
    LocalDateTime changedAt;
    boolean isAnonymous;
    Long baseCommentId;
    List<CommentResponseDto> children;

}
