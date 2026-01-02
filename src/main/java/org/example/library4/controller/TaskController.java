package org.example.library4.controller;

import org.example.library4.model.Task;
import org.example.library4.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository; // Подключаем базу данных

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // Берем всё из базы
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        return taskRepository.findById(id) // Ищем в базе по ID
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        if (task.getStatus() == null) task.setStatus("todo");
        // Сохраняем в базу. ID база назначит сама автоматически
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task taskDetails) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(taskDetails.getTitle());
                    task.setDescription(taskDetails.getDescription());
                    task.setStatus(taskDetails.getStatus());
                    return ResponseEntity.ok(taskRepository.save(task)); // Сохраняем изменения
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> patchTask(@PathVariable int id, @RequestBody Task taskDetails) {
        return taskRepository.findById(id)
                .map(task -> {
                    if (taskDetails.getStatus() != null) task.setStatus(taskDetails.getStatus());
                    return ResponseEntity.ok(taskRepository.save(task)); // Сохраняем изменения
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id); // Удаляем из базы
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}