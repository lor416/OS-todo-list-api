package org.example.library4.repository;

import org.example.library4.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    // Spring сам создаст методы: save(), findAll(), deleteById() и т.д.
}