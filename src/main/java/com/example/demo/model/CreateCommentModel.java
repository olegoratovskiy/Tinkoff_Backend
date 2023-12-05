package com.example.demo.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateCommentModel {
    @NotNull
    String content;
    long postId;
    @NotNull
    LocalDateTime createdAt;
    boolean isAnonymous;
}
