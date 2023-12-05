package com.example.demo.controller;

import com.example.demo.dto.request.WorkRequestDto;
import com.example.demo.dto.response.WorkResponseDto;
import com.example.demo.entity.Work;
import com.example.demo.mapper.WorkMapper;
import com.example.demo.service.WorkService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/works")
public class WorkController {
    private WorkService workService;
    private WorkMapper workMapper;

    @GetMapping("/get/all")
    public List<WorkResponseDto> getAllYears() {
        List<WorkResponseDto> list = new ArrayList<>();
        List<Work> postList = workService.getAllWork();
        for (Work work : postList) {
            list.add(workMapper.fromModelToDto(work));
        }
        return list;
    }

    @GetMapping("/get/{id}")
    public WorkResponseDto getWorkById(@PathVariable @Valid Long id) {
        return workMapper.fromModelToDto(workService.getWorkById(id));
    }



    @GetMapping("/get")
    public List<WorkResponseDto> getAllWorks(@RequestParam @Valid Long subjectId) {
        List<WorkResponseDto> list = new ArrayList<>();
        List<Work> worksList = workService.getAllWorksBySubId(subjectId);
        for (Work work : worksList) {
            list.add(workMapper.fromModelToDto(work));
        }
        return list;
    }


    @PostMapping("/create")
    public WorkResponseDto createWork(@RequestBody @Valid WorkRequestDto workRequestDto) {
        Work model = workMapper.fromDtoToModel(workRequestDto);
        Long subjectId = workRequestDto.getIdSubject();

        return workMapper.fromModelToDto(workService.createWork(model, subjectId));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteWorkById(@PathVariable @Valid Long id) {
        workService.deleteWork(id);
    }
}
