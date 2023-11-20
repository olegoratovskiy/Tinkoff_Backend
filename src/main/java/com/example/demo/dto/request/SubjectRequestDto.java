package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubjectRequestDto {
    private String name;
    private Long yearId;
    private List<Long> worksId;
}
