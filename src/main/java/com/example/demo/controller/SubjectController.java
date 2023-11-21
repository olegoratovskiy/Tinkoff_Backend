package com.example.demo.controller;

import com.example.demo.dto.request.SubjectRequestDto;
import com.example.demo.dto.response.SubjectResponseDto;
import com.example.demo.entity.Subject;
import com.example.demo.mapper.SubjectMapper;
import com.example.demo.service.SubjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/subjects")
@AllArgsConstructor
public class SubjectController {
    private SubjectService service;
    private SubjectMapper subjectMapper;

    @PostMapping("/create")
    public SubjectResponseDto createSubject(@RequestBody @Valid SubjectRequestDto subjectDto) {
        return subjectMapper.fromModelToDto(
                service.createSubject
                        (subjectMapper.fromDtoToModel(subjectDto),subjectDto.getYearId()));
    }

    @GetMapping("/find/{id}")
    public SubjectResponseDto getSubject(@PathVariable @Valid Long id) {
        return subjectMapper.fromModelToDto(service.getSubject(id));
    }

    @GetMapping("/find_all")
    public List<SubjectResponseDto> getAllYears() {
        List<SubjectResponseDto> list = new ArrayList<>();
        List<Subject> postList = service.getAllSubject();
        for (Subject subject : postList) {
            list.add(subjectMapper.fromModelToDto(subject));
        }
        return list;
    }

    @GetMapping("/find_all_by_year_id/{id}")
    public List<SubjectResponseDto> getAllSubject(@PathVariable @Valid Long id) {
        List<SubjectResponseDto> list = new ArrayList<>();
        List<Subject> subjects = service.getAllByYearId(id);
        for (Subject subject : subjects) {
            list.add(subjectMapper.fromModelToDto(subject));
        }
        return list;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @Valid Long id) {
        service.deleteSubject(id);
    }
}
