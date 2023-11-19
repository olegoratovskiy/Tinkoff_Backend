package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Entity
@Table(name = "post_ref")
public class PostEntity {
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "post_ref_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    private Long id;
    @Column(name = "title")
    private String title;
    @ManyToOne
    @JoinColumn(name = "work_id")
    private WorkEntity workId;
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "postId", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "postId", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<FileEntity> files;
}
