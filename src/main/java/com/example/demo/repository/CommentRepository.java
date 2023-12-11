package com.example.demo.repository;

import com.example.demo.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPostIdId(long postId, Pageable pageable);
    void deleteAllByCreatedAtBefore(LocalDateTime beforeLocalDateTime);
    Page<Comment> findAllByNewsId(long news, Pageable pageable);
//    Page<Comment> findAllByParentCommentId(long parentCommentId, Pageable pageable);
    List<Comment> findAllByParentCommentId(long parentCommentId);
}
