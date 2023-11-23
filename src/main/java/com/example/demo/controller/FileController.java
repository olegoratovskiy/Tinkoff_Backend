package com.example.demo.controller;

import com.example.demo.dto.response.FileResponseDto;
import com.example.demo.mapper.FileDtoMapper;
import com.example.demo.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping(value = "save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public FileResponseDto saveFile(@RequestPart MultipartFile request, long postId) throws IOException {
        var savedFile = fileService.saveFile(request.getBytes(), postId);
        return mapper.entityToResponse(savedFile);
    }

    @GetMapping("/{fileId}")
    public FileResponseDto getFile(@PathVariable long fileId) {
        return mapper.entityToResponse(fileService.getFile(fileId));
    }
}
