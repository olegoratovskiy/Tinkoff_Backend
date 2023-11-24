package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectResponseDto {
    private Long id;
    private String name;
    private EducationYearResponseDto educationYearId;
}
