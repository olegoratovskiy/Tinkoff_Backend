package com.example.demo.controller;

import com.example.demo.dto.request.CreateCommentRequestDto;
import com.example.demo.dto.response.CommentResponseDto;
import com.example.demo.dto.response.CommentsResponseDto;
import com.example.demo.dto.response.PageInfoResponse;
import com.example.demo.entity.Comment;
import com.example.demo.mapper.CommentDtoMapper;
import com.example.demo.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentDtoMapper mapper;

    public CommentController(CommentService commentService, CommentDtoMapper mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }

    @PostMapping("create")
    public CommentResponseDto createComment(@RequestBody @Valid CreateCommentRequestDto request) {
        var createCommentModel = mapper.requestToEntity(request);
        var createdComment = commentService.createComment(createCommentModel);
        return mapper.entityToResponse(createdComment);
    }

    @GetMapping("/{postId}")
    public CommentsResponseDto getComments(
            @PathVariable long postId,
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        Page<Comment> comments = commentService.getComments(postId, pageNumber, pageSize);
        PageInfoResponse pageInfo = new PageInfoResponse(
                comments.getTotalPages(),
                comments.getTotalElements(),
                comments.getNumber(),
                comments.getNumberOfElements()
        );
        return new CommentsResponseDto(
                comments.getContent().stream().map(mapper::entityToResponse).toList(),
                pageInfo
        );
    }
}
