package com.example.demo.dto.response;

import com.example.demo.enums.FileType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileResponseDto {
    private Long id;
    private byte[] content;
    private FileType fileType;
}
