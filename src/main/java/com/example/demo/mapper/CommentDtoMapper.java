package com.example.demo.mapper;

import com.example.demo.dto.request.CreateCommentRequestDto;
import com.example.demo.dto.response.CommentResponseDto;
import com.example.demo.entity.Comment;
import com.example.demo.model.CreateCommentModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentDtoMapper {
    private final PostMapper postMapper;

    public CommentDtoMapper(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public CreateCommentModel requestToEntity(CreateCommentRequestDto request) {
        return new CreateCommentModel(
                request.getContent(),
                request.getPostId(),
                LocalDateTime.now()
        );
    }

    public CommentResponseDto entityToResponse(Comment entity) {
        return new CommentResponseDto(
                entity.getId(),
                entity.getContent(),
                postMapper.fromModelToDto(entity.getPostId()),
                entity.getCreatedAt()
        );
    }
}
