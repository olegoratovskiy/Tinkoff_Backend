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
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
