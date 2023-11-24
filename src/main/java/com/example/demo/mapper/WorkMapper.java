package com.example.demo.mapper;

import com.example.demo.dto.request.WorkRequestDto;
import com.example.demo.dto.response.WorkResponseDto;
import com.example.demo.entity.Work;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkMapper {
    WorkResponseDto fromModelToDto(Work work);

    Work fromDtoToModel(WorkRequestDto workRequestDto);
}
