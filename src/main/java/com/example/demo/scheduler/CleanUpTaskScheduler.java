package com.example.demo.scheduler;

import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class CleanUpTaskScheduler {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CleanUpTaskScheduler(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Scheduled(cron = "*/30 * * * * *")
    @SchedulerLock(name = "CleanUpTaskScheduler_scheduledTask")
    public void scheduledTask() {
        System.out.println("Hello, world");
    }

    @Scheduled(cron = "0 */15 * * * *")
    @SchedulerLock(name = "CleanUpTaskScheduler_cleanUpComments")
    @Transactional
    public void cleanUpComments() {
        LocalDateTime beforeLocalDateTime = LocalDateTime.now().minusYears(2);
        commentRepository.deleteAllByCreatedAtBefore(beforeLocalDateTime);
    }

    @Scheduled(cron = "0 */15 * * * *")
    @SchedulerLock(name = "CleanUpTaskScheduler_cleanUpPosts")
    @Transactional
    public void cleanUpPosts() {
        LocalDateTime beforeLocalDateTime = LocalDateTime.now().minusYears(2);
        postRepository.deleteAllByCreatedAtBefore(beforeLocalDateTime);
    }
}
