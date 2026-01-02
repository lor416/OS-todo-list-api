package org.example.library4.model;

import jakarta.persistence.*;

@Entity // Говорит, что это таблица
@Table(name = "tasks")
public class Task {
    @Id // Это первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Авто-инкремент в БД
    private Integer id; // ЗАМЕНИ int НА Integer
    private String title;
    private String description;
    private String status;

    // ОБЯЗАТЕЛЬНО добавь пустой конструктор
    public Task() {}

    // Геттеры и сеттеры
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}