package com.example.demo.service;

import com.example.demo.entity.EducationYear;
import com.example.demo.repository.EducationYearRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EducationYearService {
    private EducationYearRepository yearRepository;

    public EducationYear getEducationYear(Long id) {
        return yearRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<EducationYear> getAllEducationYear() {
        return yearRepository.findAll();
    }
}
