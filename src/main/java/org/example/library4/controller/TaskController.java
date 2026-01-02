package org.example.library4.controller;

import org.example.library4.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private List<Task> tasks = new ArrayList<>();
    private int currentId = 1;

    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        task.setId(currentId++);
        if (task.getStatus() == null) task.setStatus("todo");
        tasks.add(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task taskDetails) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setTitle(taskDetails.getTitle());
                task.setDescription(taskDetails.getDescription());
                task.setStatus(taskDetails.getStatus());
                return ResponseEntity.ok(task);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> patchTask(@PathVariable int id, @RequestBody Task taskDetails) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                if (taskDetails.getStatus() != null) task.setStatus(taskDetails.getStatus());
                return ResponseEntity.ok(task);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}