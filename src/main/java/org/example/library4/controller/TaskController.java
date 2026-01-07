package org.example.library4.controller;

import jakarta.validation.Valid;
import org.example.library4.model.Task;
import org.example.library4.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping
    @Cacheable(value = "tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "tasks", key = "#id")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @CacheEvict(value = "tasks", allEntries = true)
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        if (task.getStatus() == null) task.setStatus("todo");
        Task savedTask = taskRepository.save(task);
        eventPublisher.publishEvent(savedTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PatchMapping("/{id}")
    @CacheEvict(value = "tasks", allEntries = true)
    public ResponseEntity<Task> updateTaskStatus(@PathVariable int id, @RequestBody Task taskDetails) {
        return taskRepository.findById(id).map(task -> {
            if (taskDetails.getStatus() != null) task.setStatus(taskDetails.getStatus());
            if (taskDetails.getTitle() != null) task.setTitle(taskDetails.getTitle());
            return ResponseEntity.ok(taskRepository.save(task));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "tasks", allEntries = true)
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}