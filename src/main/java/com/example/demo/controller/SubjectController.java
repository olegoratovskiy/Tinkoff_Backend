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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/subjects")
@AllArgsConstructor
public class SubjectController {
    private SubjectService service;
    private SubjectMapper subjectMapper;

    @PostMapping("/create")
    public SubjectResponseDto createSubject(@RequestBody @Valid SubjectRequestDto subjectDto) {
        Subject model = subjectMapper.fromDtoToModel(subjectDto);
        Long yearId = subjectDto.getYearId();

        return subjectMapper.fromModelToDto(service.createSubject(model, yearId));
    }

    @GetMapping("/get/all")
    public List<SubjectResponseDto> getAllYears() {
        List<SubjectResponseDto> list = new ArrayList<>();
        List<Subject> postList = service.getAllSubject();
        for (Subject subject : postList) {
            list.add(subjectMapper.fromModelToDto(subject));
        }
        return list;
    }

    @GetMapping("/get/{id}")
    public SubjectResponseDto getSubject(@PathVariable @Valid Long id) {
        return subjectMapper.fromModelToDto(service.getSubject(id));
    }

    @GetMapping("/get")
    public List<SubjectResponseDto> getAllSubject(@RequestParam @Valid Long yearId) {
        List<SubjectResponseDto> list = new ArrayList<>();
        List<Subject> subjects = service.getAllByYearId(yearId);
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
