package com.example.demo.dto.response;

import com.example.demo.entity.File;
import lombok.Data;


@Data
public class UserCabinetResponseDto {
    private Long id;
    private String name;
    private String role;
    private String gender;
}
