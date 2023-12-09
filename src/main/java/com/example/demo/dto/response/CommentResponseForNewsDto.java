package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponseForNewsDto {
    Long id;
    String content;
    UserAccountResponseDto author;
    NewsResponseDto news;
    LocalDateTime createdAt;
    boolean isAnonymous;
    Long baseCommentId;
}
