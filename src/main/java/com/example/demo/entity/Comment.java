package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment_jn")
public class Comment {
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "comment_jn_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    private Long id;
    @Column(name = "content")
    private String content;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "changed_at")
    private LocalDateTime changedAt;
    @Column(name = "is_anonymous")
    private boolean isAnonymous;
    @Column(name = "parent_comment_id")
    private Long parentCommentId;

}
