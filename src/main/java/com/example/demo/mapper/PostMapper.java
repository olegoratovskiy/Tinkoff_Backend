package com.example.demo.mapper;

import com.example.demo.dto.request.PostRequestDto;
import com.example.demo.dto.response.PostResponseDto;
import com.example.demo.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponseDto fromModelToDto(Post post);

    Post fromDtoToModel(PostRequestDto postRequestDto);

}
