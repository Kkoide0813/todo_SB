package com.example.todo.service.task;

public record TaskEntity(

        long id,

        String description,

        String summary,

        TaskStatus status
) {
}