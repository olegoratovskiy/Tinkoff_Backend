package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class WorkResponseDto {
    private Long id;
    private String typeOfWork;
    private SubjectResponseDto subjectId;
    private List<Long> postId;
}
