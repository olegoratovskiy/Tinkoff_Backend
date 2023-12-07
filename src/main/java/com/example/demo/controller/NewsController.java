package com.example.demo.controller;

import com.example.demo.dto.request.NewsRequestDto;
import com.example.demo.dto.response.NewsResponseDto;
import com.example.demo.mapper.NewsMapper;
import com.example.demo.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @PostMapping("/create")
    public NewsResponseDto createNews(@RequestBody NewsRequestDto newsRequestDto) {
        return newsMapper.fromModelToDto(newsService.createNews(newsMapper.
                fromDtoToModel(newsRequestDto), newsRequestDto.getToken()));
    }

    @GetMapping("/get/all")
    public List<NewsResponseDto> getAllNews() {
        return newsService.getAll()
                .stream()
                .map(newsMapper::fromModelToDto)
                .toList();
    }

    @PutMapping("/update")
    public NewsResponseDto updateNews(@RequestBody NewsRequestDto newsRequestDto, long idNews) {
        return newsMapper.fromModelToDto(newsService
                .update(newsMapper.fromDtoToModel(newsRequestDto), idNews));

    }

    @DeleteMapping("/delete/{id}")
    public void deleteNews(@PathVariable @Valid long id) {
        newsService.delete(id);
    }
}
