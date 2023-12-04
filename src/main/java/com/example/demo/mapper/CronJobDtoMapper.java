package com.example.demo.mapper;

import com.example.demo.dto.response.CronJobResponseDto;
import com.example.demo.entity.CronJob;
import org.springframework.stereotype.Component;

@Component
public class CronJobDtoMapper {
    public CronJobResponseDto entityToDto(CronJob entity) {
        return new CronJobResponseDto(entity.getId(), entity.getCron(), entity.getTriggeredAt());
    }
}
