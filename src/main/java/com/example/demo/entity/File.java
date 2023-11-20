package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "file_jn")
public class File {
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "file_jn_id_seq)", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    private Long id;
    @Column(name = "file_url")
    private String fileUrl;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postId;
}
