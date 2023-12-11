package com.example.demo.mapper;

import com.example.demo.dto.request.CreateCommentForNewsRequestDto;
import com.example.demo.dto.request.CreateCommentRequestDto;
import com.example.demo.dto.request.UpdateCommentRequestDto;
import com.example.demo.dto.response.CommentResponseDto;
import com.example.demo.dto.response.CommentResponseForNewsDto;
import com.example.demo.entity.Comment;
import com.example.demo.model.CreateCommentForNewsModel;
import com.example.demo.model.CreateCommentModel;
import com.example.demo.model.UpdateCommentModel;
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

    public CreateCommentForNewsModel requestToEntity(CreateCommentForNewsRequestDto request) {
        return new CreateCommentForNewsModel(
                request.getContent(),
                request.getPostId(),
                LocalDateTime.now(),
                request.isAnonymous(),
                request.getParentCommentId()
        );
    }

    public UpdateCommentModel requestToModel(UpdateCommentRequestDto request) {
        return new UpdateCommentModel(
                request.getContent(),
                request.getCommentId(),
                request.getToken(),
                LocalDateTime.now()
        );
    }

    public CommentResponseDto entityToResponse(Comment entity) {
        return new CommentResponseDto(
                entity.getId(),
                entity.getContent(),
                userMapper.fromModelToAccountDto(entity.getAuthor()),
                postMapper.fromModelToDto(entity.getPostId()),
                entity.getCreatedAt(),
                entity.getChangedAt(),
                entity.isAnonymous(),
                entity.getParentCommentId()
        );
    }

    public CommentResponseForNewsDto entityToResponseNews(Comment entity) {
        return new CommentResponseForNewsDto(
                entity.getId(),
                entity.getContent(),
                userMapper.fromModelToAccountDto(entity.getAuthor()),
                mapper.fromModelToDto(entity.getNews()),
                entity.getCreatedAt(),
                entity.isAnonymous(),
                entity.getParentCommentId()
        );
    }
}
