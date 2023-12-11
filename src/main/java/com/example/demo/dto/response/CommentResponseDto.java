package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    Long id;
    String content;
    UserAccountResponseDto author;
    PostResponseDto postId;
    LocalDateTime createdAt;
    LocalDateTime changedAt;
    boolean isAnonymous;
    Long baseCommentId;
}
