package com.example.demo.controller;

import com.example.demo.dto.request.CreateCommentRequestDto;
import com.example.demo.dto.response.CommentResponseDto;
import com.example.demo.mapper.CommentDtoMapper;
import com.example.demo.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<CommentResponseDto> getComments(@PathVariable long postId) {
        return commentService.getComments(postId).stream()
                .map(mapper::entityToResponse)
                .toList();
    }
}
