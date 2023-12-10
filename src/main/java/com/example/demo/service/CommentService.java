package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.model.CreateCommentForNewsModel;
import com.example.demo.model.CreateCommentModel;
import com.example.demo.model.UpdateCommentModel;
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

        comment.setAuthor(
                userService.findByUserName(jwtTokenUtils.getUsername(token))
                        .orElseThrow(RuntimeException::new)
        );
        comment.setAnonymous(model.isAnonymous());
        return commentRepository.save(comment);
    }

    public Comment updateComment(UpdateCommentModel updateCommentModel) {
        var commentId = updateCommentModel.getCommentId();
        var commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("No comment with id: " + commentId));

        var jwtToken = updateCommentModel.getToken();
        var updatedCommentUser = userService.findByUserName(jwtTokenUtils.getUsername(updateCommentModel.getToken()))
                .orElseThrow(() -> new IllegalArgumentException("No user with jwt token: " + jwtToken));

        if (!updatedCommentUser.getComments().contains(commentEntity)) {
            String message = "User with jwt token={} can't change comment with id={}";
            throw new IllegalArgumentException(String.format(message, jwtToken, commentId));
        }

        commentEntity.setContent(updateCommentModel.getContent());
        commentEntity.setChangedAt(updateCommentModel.getChangedAt());
        return commentRepository.save(commentEntity);
    }

    public Comment createCommentForNews(CreateCommentForNewsModel model, String token) {
        var createdCommentForNewsUser = userService.findByUserName(jwtTokenUtils.getUsername(token))
                .orElseThrow(() -> new IllegalArgumentException("No user with jwt token: " + token));

        if (model.getParentCommentId() != null) {
            var parentCommentId = model.getParentCommentId();
            var parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new IllegalArgumentException("No comment with id: " + parentCommentId));
            if (parentComment.getParentCommentId() != null) {
                throw new IllegalArgumentException("Can't create reply on reply");
            }
        }

        var news = newsRepository.findById(model.getPostId())
                .orElseThrow(RuntimeException::new);

        var comment = new Comment();
        comment.setContent(model.getContent());
        comment.setNews(news);
        comment.setCreatedAt(model.getCreatedAt());
        comment.setParentCommentId(model.getParentCommentId());
        comment.setAuthor(createdCommentForNewsUser);
        comment.setAnonymous(model.isAnonymous());

        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsFromNews(long newsId) {
        var news = newsRepository.findById(newsId).orElseThrow(RuntimeException::new);
        return news.getComments();
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

    public Page<Comment> getRepliedCommentsForNews(long commentId, int pageNumber, int pageSize) {
        return commentRepository.findAllByParentCommentId(
                commentId,
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
