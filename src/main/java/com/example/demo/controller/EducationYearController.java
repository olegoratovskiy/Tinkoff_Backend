package com.example.demo.controller;

import com.example.demo.dto.response.EducationYearResponseDto;
import com.example.demo.mapper.EducationYearMapper;
import com.example.demo.entity.EducationYear;
import com.example.demo.service.EducationYearService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/years")
public class EducationYearController {
    private EducationYearMapper educationYearMapper;
    private EducationYearService educationYearService;

    @GetMapping("find_all")
    public List<EducationYearResponseDto> getAllYears() {
        List<EducationYearResponseDto> list = new ArrayList<>();
        List<EducationYear> educationYearList = educationYearService.getAllEducationYear();
        for (EducationYear educationYear : educationYearList) {
            list.add(educationYearMapper.fromModelToDto(educationYear));
        }
        return list;
    }

    @GetMapping("find/{id}")
    public EducationYearResponseDto getEducationYearById(@PathVariable @Valid Long id) {
        return educationYearMapper.fromModelToDto(educationYearService.getEducationYear(id));
    }

}
