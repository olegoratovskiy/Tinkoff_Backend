package com.example.demo.mapper;

import com.example.demo.dto.request.CreateCommentRequestDto;
import com.example.demo.dto.response.CommentResponseDto;
import com.example.demo.dto.response.CommentResponseForNewsDto;
import com.example.demo.entity.Comment;
import com.example.demo.model.CreateCommentModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentDtoMapper {
    private final PostMapper postMapper;
    private final UserMapper userMapper;

    private final NewsMapper mapper;

    public CommentDtoMapper(PostMapper postMapper, UserMapper userMapper, NewsMapper mapper) {
        this.postMapper = postMapper;
        this.userMapper = userMapper;
        this.mapper = mapper;
    }

    public CreateCommentModel requestToEntity(CreateCommentRequestDto request) {
        return new CreateCommentModel(
                request.getContent(),
                request.getPostId(),
                LocalDateTime.now(),
                request.isAnonymous()
        );
    }

    public CommentResponseDto entityToResponse(Comment entity) {
        return new CommentResponseDto(
                entity.getId(),
                entity.getContent(),
                userMapper.fromModelToAccountDto(entity.getAuthor()),
                postMapper.fromModelToDto(entity.getPostId()),
                entity.getCreatedAt(),
                entity.isAnonymous()


        );
    }

    public CommentResponseForNewsDto entityToResponseNews(Comment entity) {
        return new CommentResponseForNewsDto(
                entity.getId(),
                entity.getContent(),
                userMapper.fromModelToAccountDto(entity.getAuthor()),
                mapper.fromModelToDto(entity.getNews()),
                entity.getCreatedAt(),
                entity.isAnonymous()
        );
    }
}
