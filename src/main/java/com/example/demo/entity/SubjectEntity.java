package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Entity
@Table(name = "subject_ref")
public class SubjectEntity {
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "subject_ref_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "education_year_id")
    private YearEntity courseId;

    @OneToMany(mappedBy = "subjectId", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<WorkEntity> works;
}
