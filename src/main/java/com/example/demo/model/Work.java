package com.example.demo.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "work_ref")
public class Work {
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "work_ref_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    private Long id;
    @Column(name = "type_of_work")
    private String typeOfWork;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subjectId;

    @OneToMany(mappedBy = "workId", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Post> posts;
}
