package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String description;
    private Long idWork;
    private List<Long> commentsId;
    private List<Long>  filesId;
}
