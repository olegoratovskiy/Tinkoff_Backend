package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "file_jn")
public class FileEntity {
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "file_jn_id_seq)", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    private Long id;
    @Column(name = "title")
    private String title;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postId;
}
