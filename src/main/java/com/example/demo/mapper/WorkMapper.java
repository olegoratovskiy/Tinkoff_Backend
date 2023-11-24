package com.example.demo.mapper;

import com.example.demo.dto.request.WorkRequestDto;
import com.example.demo.dto.response.WorkResponseDto;
import com.example.demo.entity.Post;
import com.example.demo.entity.Work;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkMapper {
    @Mapping(target = "postId", source = "work.posts", qualifiedByName = "setPostId")
    WorkResponseDto fromModelToDto(Work work);

    Work fromDtoToModel(WorkRequestDto workRequestDto);


    @Named("setPostId")
    static List<Long> setPostId(List<Post> posts) {
        if (posts != null) {
            return posts.stream()
                    .map(Post::getId)
                    .toList();
        }
        return new ArrayList<>();
    }
}
