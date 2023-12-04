package com.example.demo.service;

import com.example.demo.entity.CronJob;
import com.example.demo.repository.CronJobRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CronJobService {

    private final CronJobRepository cronJobRepository;

    public CronJobService(CronJobRepository cronJobRepository) {
        this.cronJobRepository = cronJobRepository;
    }

    public CronJob getCronJob(long cronJobId) {
        return getCronJobOrThrow(cronJobId);
    }

    public Long changeCron(long cronJobId, String cron) {
        var cronJob = getCronJobOrThrow(cronJobId);
        cronJob.setCron(cron);
        return cronJobRepository.save(cronJob).getId();
    }

    private CronJob getCronJobOrThrow(long cronJobId) {
        return cronJobRepository.findById(cronJobId).orElseThrow(EntityNotFoundException::new);
    }

}
