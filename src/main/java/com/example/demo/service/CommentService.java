package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.model.CreateCommentModel;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public Comment createComment(CreateCommentModel model) {
        var post = getPostByIdOrThrow(model.getPostId());

        var comment = new Comment();
        comment.setContent(model.getContent());
        comment.setPostId(post);
        comment.setCreatedAt(model.getCreatedAt());

        return commentRepository.save(comment);
    }

    public List<Comment> getComments(long postId) {
        var post = getPostByIdOrThrow(postId);
        return post.getComments();
    }

    private Post getPostByIdOrThrow(long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No post with id: " + id)
        );
    }
}
