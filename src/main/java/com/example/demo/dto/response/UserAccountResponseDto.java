package com.example.demo.dto.response;

import lombok.Data;


@Data
public class UserAccountResponseDto {
    private Long id;
    private String name;
    private String role;
    private String gender;
}
