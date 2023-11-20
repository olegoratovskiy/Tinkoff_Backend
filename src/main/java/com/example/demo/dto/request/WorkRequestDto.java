package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class WorkRequestDto {
    private String typeOfWork;
    private Long idSubject;
    private List<Long> postId;

}
