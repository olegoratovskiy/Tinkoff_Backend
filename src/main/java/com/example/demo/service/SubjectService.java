package com.example.demo.service;

import com.example.demo.entity.Subject;
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

    public Subject createSubject(Subject subject,Long id){
        subject.setEducationYearId(educationYearService.getEducationYear(id));
        return subjectRepository.save(subject);
    }

    public Subject getSubject(Long id){
        return subjectRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Subject> getAllSubject(){
        return subjectRepository.findAll();
    }

    @Transactional
    public void deleteSubject(Long id){
        subjectRepository.deleteById(id);
    }


}
