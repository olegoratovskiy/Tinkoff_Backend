package com.example.demo.scheduler;

import com.example.demo.entity.CronJob;
import com.example.demo.repository.CronJobRepository;
import com.example.demo.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class CleanUpTaskScheduler {

    private final CronJobRepository cronJobRepository;
    private final PostRepository postRepository;

    public CleanUpTaskScheduler(CronJobRepository cronJobRepository, PostRepository postRepository) {
        this.cronJobRepository = cronJobRepository;
        this.postRepository = postRepository;
    }

    @Scheduled(cron = "*/30 * * * * *")
    @SchedulerLock(name = "CleanUpTaskScheduler_cleanUpPosts")
    @Transactional
    public void cleanUpPosts() {
        CronJob cronJob = cronJobRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
        CronExpression cronExpression = CronExpression.parse(cronJob.getCron());
        LocalDateTime startJobAfter = cronExpression.next(cronJob.getTriggeredAt());

        if (LocalDateTime.now().isAfter(startJobAfter)) {
            postRepository.deleteAllByCreatedAtBefore(LocalDateTime.now());

            cronJob.setTriggeredAt(LocalDateTime.now());
            cronJobRepository.save(cronJob);
        }
    }

}
