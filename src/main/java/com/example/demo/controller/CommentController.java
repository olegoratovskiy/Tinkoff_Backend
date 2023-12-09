package com.example.demo.controller;

import com.example.demo.dto.request.CreateCommentForNewsRequestDto;
import com.example.demo.dto.request.CreateCommentRequestDto;
import com.example.demo.dto.request.UpdateCommentRequestDto;
import com.example.demo.dto.response.*;
import com.example.demo.entity.Comment;
import com.example.demo.mapper.CommentDtoMapper;
import com.example.demo.service.CommentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Transactional
    @PostMapping("/create")
    public CommentResponseDto createComment(@RequestBody @Valid CreateCommentRequestDto request) {
        String token = request.getToken();
        var createCommentModel = mapper.requestToEntity(request);
        var createdComment = commentService.createComment(createCommentModel, token);

        return mapper.entityToResponse(createdComment);
    }

    @PostMapping("/update")
    public CommentResponseDto updateComment(@RequestBody @Valid UpdateCommentRequestDto request) {
        var updateCommentModel = mapper.requestToModel(request);
        var updatedComment = commentService.updateComment(updateCommentModel);
        return mapper.entityToResponse(updatedComment);
    }

    @Transactional
    @PostMapping("/create-for-news")
    public CommentResponseForNewsDto createCommentForNews(@RequestBody @Valid CreateCommentForNewsRequestDto request) {
        String token = request.getToken();
        var createCommentModel = mapper.requestToEntity(request);
        var createdComment = commentService.createCommentForNews(createCommentModel, token);

        return mapper.entityToResponseNews(createdComment);
    }

    @Transactional
    @GetMapping("/get/all")
    public List<CommentResponseDto> getAllComments() {
        return commentService.getAllComments().stream()
                .map(mapper::entityToResponse)
                .toList();
    }

    @Transactional
    @GetMapping("/get")
    public CommentsResponseDto getComments(
            @RequestParam long postId,
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


    @GetMapping("/get/all/for-news")
    public List<CommentResponseForNewsDto> getCommentsForNews(@RequestParam long newsId) {
        return commentService.getAllCommentsFromNews(newsId).stream()
                .map(mapper::entityToResponseNews)
                .toList();
    }

    @Transactional
    @GetMapping("/get-for-news")
    public CommentsResponseDtoForNews getCommentsForNews(
            @RequestParam long postId,
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        Page<Comment> comments = commentService.getCommentsForNews(postId, pageNumber, pageSize);
        PageInfoResponse pageInfo = new PageInfoResponse(
                comments.getTotalPages(),
                comments.getTotalElements(),
                comments.getNumber(),
                comments.getNumberOfElements()
        );
        return new CommentsResponseDtoForNews(
                comments.getContent().stream().map(mapper::entityToResponseNews).toList(),
                pageInfo
        );
    }

    @Transactional
    @GetMapping
    public CommentsResponseDtoForNews getRepliedCommentsForNews(
            @RequestParam long commentId,
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        Page<Comment> comments = commentService.getRepliedCommentsForNews(commentId, pageNumber, pageSize);
        PageInfoResponse pageInfo = new PageInfoResponse(
                comments.getTotalPages(),
                comments.getTotalElements(),
                comments.getNumber(),
                comments.getNumberOfElements()
        );
        return new CommentsResponseDtoForNews(
                comments.getContent().stream().map(mapper::entityToResponseNews).toList(),
                pageInfo
        );
    }

    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable @Valid Long id) {
        commentService.deleteComment(id);
    }
}
