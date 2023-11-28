package com.example.demo.dto.response;

import com.example.demo.entity.File;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private String title;
    private String description;
    private WorkResponseDto workId;
    private List<File> files;
}
