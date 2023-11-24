package com.example.demo.mapper;

import com.example.demo.dto.response.EducationYearResponseDto;
import com.example.demo.entity.EducationYear;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EducationYearMapper {
    EducationYearResponseDto fromModelToDto(EducationYear educationYear);

}
