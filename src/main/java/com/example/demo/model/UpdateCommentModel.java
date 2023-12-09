package com.example.demo.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateCommentModel {
    @NotNull
    String content;
    long commentId;
    String token;
    LocalDateTime changedAt;
}
