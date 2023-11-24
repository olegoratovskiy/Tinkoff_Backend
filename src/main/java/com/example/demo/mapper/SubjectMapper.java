package com.example.demo.mapper;

import com.example.demo.dto.request.SubjectRequestDto;
import com.example.demo.dto.response.SubjectResponseDto;
import com.example.demo.entity.Subject;
import com.example.demo.entity.Work;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(target = "worksId", source = "subject.works", qualifiedByName = "setWorksId")
    SubjectResponseDto fromModelToDto(Subject subject);

    Subject fromDtoToModel(SubjectRequestDto subjectRequestDto);


    @Named("setWorksId")
    static List<Long> setWorksId(List<Work> works) {
        if (works != null) {
            return works.stream()
                    .map(Work::getId)
                    .toList();
        }
        return new ArrayList<>();
    }

}
