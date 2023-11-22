package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequestDto {
    @NotNull(message = "пост не может быть без заголовка")
    @Size(max = 30,message = "превышен лимит заголовка")
    @Size(min = 5,message = "слишком маленький заголовок")
    private String title;
    @NotNull(message = "пост не может быть без описания")
    @Size(max = 250,message = "превышен лимит описания")
    private String description;
    private Long idWork;
    private List<Long> commentsId;
    private List<Long>  filesId;
}
