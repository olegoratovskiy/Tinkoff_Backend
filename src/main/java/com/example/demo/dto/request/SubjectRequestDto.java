package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequestDto {
    @NotNull(message = "название предмета не может быть пустым")
    @Size(max = 10, message = "превышен лимит названия")
    @Size(min = 2, message = "слишком короткое название")
    private String name;
    private Long yearId;
}
