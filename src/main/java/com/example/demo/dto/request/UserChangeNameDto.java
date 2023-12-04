package com.example.demo.dto.request;

import lombok.Value;


@Value
public class UserChangeNameDto {
    Long userId;
    String name;
}
