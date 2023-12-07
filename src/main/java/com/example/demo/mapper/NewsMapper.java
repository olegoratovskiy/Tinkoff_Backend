package com.example.demo.mapper;

import com.example.demo.dto.request.NewsRequestDto;
import com.example.demo.dto.response.NewsResponseDto;
import com.example.demo.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {FileDtoMapper.class})
public interface NewsMapper {
    NewsResponseDto fromModelToDto(News news);

    News fromDtoToModel(NewsRequestDto newsRequestDto);

    void update(@MappingTarget News newsToUpdate, News news);
}
