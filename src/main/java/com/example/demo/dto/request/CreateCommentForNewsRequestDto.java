package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentForNewsRequestDto {
    @NotNull
    String content;
    int postId;
    String token;
    @JsonProperty
    boolean isAnonymous;
    @Nullable
    Long parentCommentId;
}
