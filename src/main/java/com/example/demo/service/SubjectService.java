package com.example.demo.service;

import com.example.demo.model.Subject;
import com.example.demo.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public Subject createSubject(Subject subject){
        return subjectRepository.save(subject);
    }

    public Subject getSubject(Long id){
        return subjectRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void deleteSubject(Long id){
        subjectRepository.deleteById(id);
    }
}
