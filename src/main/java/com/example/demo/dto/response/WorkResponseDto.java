package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkResponseDto {
    private Long id;
    private String typeOfWork;
    private SubjectResponseDto subjectId;
}
