package com.example.demo.mapper;

import com.example.demo.dto.response.FileResponseDto;
import com.example.demo.entity.File;
import org.springframework.stereotype.Component;

@Component
public class FileDtoMapper {

    public FileDtoMapper() {
    }

    public FileResponseDto entityToResponse(File entity) {
        return new FileResponseDto(entity.getId(), entity.getContent(), entity.getFileType());
    }
}
