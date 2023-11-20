package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {
    @NotNull
    String content;
    int postId;
}
