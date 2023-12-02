package com.example.demo.repository;

import com.example.demo.entity.Post;
import com.example.demo.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByWorkIdId(long workId, Pageable pageable);

    boolean existsPostByTitleAndDescriptionAndWorkId(String title, String description, Work workId);

    void deleteAllByCreatedAtBefore(LocalDateTime beforeDateTime);
}
