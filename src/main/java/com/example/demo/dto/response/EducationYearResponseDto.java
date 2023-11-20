package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EducationYearResponseDto {
    private Long id;
    private String name;
    private List<Long> subjectId;
}
