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
@Table(name = "subject_ref")
public class Subject {
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "subject_ref_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "education_year_id")
    private EducationYear educationYearId;

    @OneToMany(mappedBy = "subjectId", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
    CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Work> works;
}
