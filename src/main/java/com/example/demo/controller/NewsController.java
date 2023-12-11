package com.example.demo.controller;

import com.example.demo.dto.request.NewsRequestDto;
import com.example.demo.dto.request.NewsRequestUpdateDto;
import com.example.demo.dto.response.NewsResponseDto;
import com.example.demo.mapper.NewsMapper;
import com.example.demo.service.NewsService;
import jakarta.transaction.Transactional;
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
        return newsMapper
                .fromModelToDto(newsService
                .createNews(newsMapper
                .fromDtoToModel(newsRequestDto),
                        newsRequestDto
                .getToken()));
    }

    @GetMapping("/get/all")
    public List<NewsResponseDto> getAllNews() {
        return newsService.getAll()
                .stream()
                .map(newsMapper::fromModelToDto)
                .toList();
    }
    @Transactional
    @GetMapping("/get/{newsId}")
    public NewsResponseDto getNews(@PathVariable @Valid Long newsId) {
        return newsMapper.fromModelToDto(newsService.getNews(newsId));
    }

    @PutMapping("/update")
    public NewsResponseDto updateNews(@RequestBody NewsRequestUpdateDto newsRequestDto) {
        var newNews = newsMapper.fromDtoUpdateToModel(newsRequestDto);
        return newsMapper.fromModelToDto(newsService
                .update(newNews, newNews.getId(), newsRequestDto.getToken()));

    }

    @DeleteMapping("/delete/{id}")
    public void deleteNews(@PathVariable @Valid long id) {
        newsService.delete(id);
    }
}
