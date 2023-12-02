package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
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
    @Transient
    private String passwordConfirm;

    @Column(name = "role")
    private String role;
    @OneToMany(mappedBy = "userId",  cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Post> posts;

    @OneToMany(mappedBy = "author",  cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

    @OneToOne(mappedBy = "userId", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private File photo;

    @Column(name = "gender")
    private String gender;

    private boolean delete = Boolean.FALSE;

}
