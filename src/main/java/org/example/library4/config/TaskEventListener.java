package org.example.library4.config;

import org.example.library4.model.Task;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class TaskEventListener {

    @EventListener
    @Async
    public void handleTaskCreated(Task task) {
        System.out.println("Async Queue: New task created with title: " + task.getTitle());
    }
}