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

    @Mapping(target = "posts", source = "workRequestDto.postId", qualifiedByName = "setPosts")
    Work fromDtoToModel(WorkRequestDto workRequestDto);


    @Named("setPosts")
    static List<Post> setPosts(List<Long> postId) {
        if (postId != null) {
            return postId.stream()
                    .map(id -> {
                        Post post = new Post();
                        post.setId(id);
                        return post;
                    }).toList();
        }
        return new ArrayList<>();
    }

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
