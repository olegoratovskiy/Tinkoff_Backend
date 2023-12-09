package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewsResponseDto {
    private Long id;
    private String title;
    private String description;
    private List<FileResponseDto> files;
    private UserAccountResponseDto userId;
}
