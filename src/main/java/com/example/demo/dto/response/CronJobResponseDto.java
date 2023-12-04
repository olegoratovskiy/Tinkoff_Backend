package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CronJobResponseDto {
    private Long id;
    private String cron;
    private LocalDateTime triggeredAt;
}
