package com.example.demo.controller;

import com.example.demo.dto.request.SubjectRequestDto;
import com.example.demo.dto.request.SubjectResponseDto;
import com.example.demo.mapper.SubjectMapper;
import com.example.demo.service.SubjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects")
@AllArgsConstructor
public class SubjectController {
    private SubjectService service;
    private SubjectMapper subjectMapper;

    @PostMapping("/create")
    public SubjectResponseDto createSubject(@RequestBody @Valid SubjectRequestDto subjectDto) {
        return subjectMapper.fromModelToDto(service.createSubject(subjectMapper.fromDtoToModel(subjectDto)));
    }

    @GetMapping("/find")
    public SubjectResponseDto getSubject(@RequestParam @Valid Long id) {
        return subjectMapper.fromModelToDto(service.getSubject(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @Valid Long id) {
        service.deleteSubject(id);
    }
}
