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

@RestController
@AllArgsConstructor
@RequestMapping("/works")
public class WorkController {
    private WorkService workService;
    private WorkMapper workMapper;

    @GetMapping("find/{id}")
    public WorkResponseDto getWorkById(@PathVariable @Valid Long id) {
        return workMapper.fromModelToDto(workService.getWorkById(id));
    }

    @GetMapping("/find_all")
    public List<WorkResponseDto> getAllYears() {
        List<WorkResponseDto> list = new ArrayList<>();
        List<Work> postList = workService.getAllWork();
        for (Work work : postList) {
            list.add(workMapper.fromModelToDto(work));
        }
        return list;
    }



    @PostMapping("create")
    public WorkResponseDto createWork(@RequestBody @Valid WorkRequestDto workRequestDto) {
        return workMapper.fromModelToDto(workService.
                createWork(workMapper.
                        fromDtoToModel(workRequestDto), workRequestDto.getIdSubject()));
    }

    @DeleteMapping("delete/{id}")
    public void deleteWorkById(@PathVariable @Valid Long id){
        workService.deleteWork(id);
    }
}
