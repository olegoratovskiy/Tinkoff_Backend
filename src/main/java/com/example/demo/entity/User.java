package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "user_jn")
public class User {
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "user_jn_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Post> posts;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Comment> comments;

}
