package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.model.CreateCommentModel;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.NewsRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.utils.JwtTokenUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final NewsRepository newsRepository;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;


    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          UserService userService,
                          JwtTokenUtils jwtTokenUtils,
                          NewsRepository newsRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.newsRepository = newsRepository;
    }

    public Comment createComment(CreateCommentModel model, String token) {
        var post = getPostByIdOrThrow(model.getPostId());

        var comment = new Comment();
        comment.setContent(model.getContent());
        comment.setPostId(post);
        comment.setCreatedAt(model.getCreatedAt());

        comment.setAuthor(userService.findByUserName(
                        jwtTokenUtils.getUsername(token))
                .orElseThrow(RuntimeException::new));
        comment.setAnonymous(model.isAnonymous());
        return commentRepository.save(comment);
    }

    public Comment createCommentForNews(CreateCommentModel model, String token) {
        var news = newsRepository.findById(model.getPostId()).orElseThrow
                (RuntimeException::new);

        var comment = new Comment();
        comment.setContent(model.getContent());
        comment.setNews(news);
        comment.setCreatedAt(model.getCreatedAt());

        comment.setAuthor(userService.findByUserName(
                        jwtTokenUtils.getUsername(token))
                .orElseThrow(RuntimeException::new));
        comment.setAnonymous(model.isAnonymous());
        return commentRepository.save(comment);
    }

    public Page<Comment> getComments(long postId, int pageNumber, int pageSize) {
        return commentRepository.findAllByPostIdId(
                postId,
                PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.asc("id")))
        );
    }

    public Page<Comment> getCommentsForNews(long postId, int pageNumber, int pageSize) {
        return commentRepository.findAllByNewsId(
                postId,
                PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.asc("id")))
        );
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    private Post getPostByIdOrThrow(long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No post with id: " + id)
        );
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
