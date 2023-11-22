package com.example.demo.service;

import com.example.demo.entity.Subject;
import com.example.demo.exceptions.CreatingExistingEntityException;
import com.example.demo.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final EducationYearService educationYearService;

    private boolean subjectExists(Subject subject, Long yearId) {
        return subjectRepository.existsSubjectByNameAndEducationYearId(subject.getName(),
                educationYearService.getEducationYear(yearId));
    }

    public Subject createSubject(Subject subject, Long yearId) {
        if (subjectExists(subject, yearId)) {
            String name = subject.getName();
            String message = String.format("Subject with Name<%s> and Year <%s> already exists", name, yearId);

            throw new CreatingExistingEntityException(message);
        }

        subject.setEducationYearId(educationYearService.getEducationYear(yearId));
        return subjectRepository.save(subject);
    }

    public Subject getSubject(Long id) {
        return subjectRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Subject> getAllSubject() {
        return subjectRepository.findAll();
    }

    public List<Subject> getAllByYearId(Long yearId) {
        return subjectRepository.findAllByEducationYearId(educationYearService.getEducationYear(yearId));
    }

    @Transactional
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }


}
