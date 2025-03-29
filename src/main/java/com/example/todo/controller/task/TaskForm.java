package com.example.todo.controller.task;

public record TaskForm(
        String summary,
        String description,
        String status
){
}
