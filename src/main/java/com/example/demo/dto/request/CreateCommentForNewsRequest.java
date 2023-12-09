package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentForNewsRequest {
    @NotNull
    String content;
    int news;
    String token;
    @JsonProperty
    boolean isAnonymous;
}
