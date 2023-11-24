package com.example.demo.mapper;

import com.example.demo.dto.request.SubjectRequestDto;
import com.example.demo.dto.response.SubjectResponseDto;
import com.example.demo.entity.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    SubjectResponseDto fromModelToDto(Subject subject);

    Subject fromDtoToModel(SubjectRequestDto subjectRequestDto);

}
