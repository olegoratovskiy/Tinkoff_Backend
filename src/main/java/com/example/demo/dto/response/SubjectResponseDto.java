package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubjectResponseDto {
    private Long id;
    private String name;
    private EducationYearResponseDto educationYearId;
    private List<Long> worksId;
}
