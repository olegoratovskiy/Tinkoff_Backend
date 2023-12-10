package com.example.demo.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateCommentForNewsModel {
    @NotNull
    String content;
    long postId;
    @NotNull
    LocalDateTime createdAt;
    boolean isAnonymous;
    @Nullable
    Long parentCommentId;
}
