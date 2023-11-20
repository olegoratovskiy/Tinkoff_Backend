package com.example.demo.mapper;

import com.example.demo.dto.response.EducationYearResponseDto;
import com.example.demo.entity.EducationYear;
import com.example.demo.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EducationYearMapper {
    @Mapping(target = "subjectId", source = "educationYear.subjects", qualifiedByName = "setSubjectId")
    EducationYearResponseDto fromModelToDto(EducationYear educationYear);

    @Named("setSubjectId")
    static List<Long> setSubjectId(List<Subject> subjects) {
        if (subjects != null) {
            return subjects.stream()
                    .map(Subject::getId)
                    .toList();
        }
        return new ArrayList<>();
    }
}
