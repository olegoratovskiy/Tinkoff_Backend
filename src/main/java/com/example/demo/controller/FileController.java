package com.example.demo.controller;

import com.example.demo.dto.response.FileResponseDto;
import com.example.demo.mapper.FileDtoMapper;
import com.example.demo.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;
    private final FileDtoMapper mapper;

    public FileController(FileService fileService, FileDtoMapper mapper) {
        this.fileService = fileService;
        this.mapper = mapper;
    }

    @PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public FileResponseDto saveFile(@RequestPart MultipartFile request, long postId) throws IOException {
        var savedFile = fileService.saveFile(request.getBytes(), postId);
        return mapper.entityToResponse(savedFile);
    }

    @PostMapping(value = "/save-for-news", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public FileResponseDto saveFileForNews(@RequestPart MultipartFile request, long postId) throws IOException {
        var savedFile = fileService.saveFileForNews(request.getBytes(), postId);
        return mapper.entityToResponse(savedFile);
    }

    @GetMapping("/get/{fileId}")
    public FileResponseDto getFile(@PathVariable long fileId) {
        return mapper.entityToResponse(fileService.getFile(fileId));
    }

    @PutMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public FileResponseDto updateFile(@RequestPart MultipartFile request, long fileId) throws IOException {
        var updateFile = fileService.updateFile(request.getBytes(), fileId);
        return mapper.entityToResponse(updateFile);
    }
}
