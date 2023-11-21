package com.example.demo.service;

import com.example.demo.entity.Work;
import com.example.demo.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;
    private final SubjectService subjectService;
    public Work createWork(Work work,Long id){
        work.setSubjectId(subjectService.getSubject(id));
        return workRepository.save(work);
    }

    public Work getWorkById(Long id){
        return workRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Work> getAllWork(){
        return workRepository.findAll();
    }

    public List<Work> getAllWorksBySubId(Long subId){
        return workRepository.findAllBySubjectId(subjectService.getSubject(subId));
    }
    @Transactional
    public void deleteWork(Long id){
        workRepository.deleteById(id);
    }
 }
