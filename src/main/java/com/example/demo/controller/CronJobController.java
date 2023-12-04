package com.example.demo.controller;

import com.example.demo.dto.response.CronJobResponseDto;
import com.example.demo.mapper.CronJobDtoMapper;
import com.example.demo.service.CronJobService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cron-job")
public class CronJobController {

    private final CronJobService cronJobService;
    private final CronJobDtoMapper mapper;

    public CronJobController(CronJobService cronJobService, CronJobDtoMapper mapper) {
        this.cronJobService = cronJobService;
        this.mapper = mapper;
    }

    @GetMapping("/{cronJobId}")
    public CronJobResponseDto getCronJob(@PathVariable long cronJobId) {
        var cronJob = cronJobService.getCronJob(cronJobId);
        return mapper.entityToDto(cronJob);
    }

    @PostMapping("/{cronJobId}/change-cron/{cron}")
    public Long changeCron(@PathVariable long cronJobId, @PathVariable String cron) {
        return cronJobService.changeCron(cronJobId, cron);
    }

}
