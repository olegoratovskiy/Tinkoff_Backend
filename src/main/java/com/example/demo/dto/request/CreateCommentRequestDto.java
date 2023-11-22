package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentRequestDto {
    @NotNull
    String content;
    int postId;
}
