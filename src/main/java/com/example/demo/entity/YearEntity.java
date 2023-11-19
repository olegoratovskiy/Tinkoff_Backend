package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Entity
@Table(name = "education_year")
public class YearEntity {
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "education_year_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "courseId", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<SubjectEntity> subjects;
}
