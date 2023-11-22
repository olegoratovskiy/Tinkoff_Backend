package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {
    @NotNull(message = "имя не может быть пустым")
    @Size(max = 20,message = "слишком большое имя")
    @Size(min = 5,message = "слишком короткое имя")
    private String name;
    private String password;
}
