package com.seongho.spring.common.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

@Service
public class SchedulingService {

    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledFuture;

    public SchedulingService(@Qualifier("taskScheduler") TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void startScheduledTask(Runnable task, Duration duration) {
        if (scheduledFuture == null || scheduledFuture.isDone()) {
            scheduledFuture = taskScheduler.scheduleAtFixedRate(task, duration);
        }
    }
    public void stopScheduledTask() {
        if (scheduledFuture != null && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(true);
        }
    }
}
