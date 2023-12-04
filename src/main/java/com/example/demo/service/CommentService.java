package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.model.CreateCommentModel;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.utils.JwtTokenUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;


    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserService userService, JwtTokenUtils jwtTokenUtils) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    public Comment createComment(CreateCommentModel model, String token) {
        var post = getPostByIdOrThrow(model.getPostId());

        var comment = new Comment();
        comment.setContent(model.getContent());
        comment.setPostId(post);
        comment.setCreatedAt(model.getCreatedAt());

        comment.setAuthor(userService.findByUserName(jwtTokenUtils.getUsername(token)).orElseThrow(RuntimeException::new));

        return commentRepository.save(comment);
    }

    public Page<Comment> getComments(long postId, int pageNumber, int pageSize) {
        return commentRepository.findAllByPostIdId(
                postId,
                PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.asc("id")))
        );
    }

    private Post getPostByIdOrThrow(long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No post with id: " + id)
        );
    }
}
