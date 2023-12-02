package com.example.demo.mapper;

import com.example.demo.dto.response.PhotoResponseDto;
import com.example.demo.entity.File;
import org.springframework.stereotype.Component;

@Component
public class PhotoDtoMapper {

    public PhotoDtoMapper() {
    }

    public PhotoResponseDto entityToResponse(File entity) {
        return new PhotoResponseDto(entity.getId(), entity.getContent(), entity.getFileType());
    }
}
