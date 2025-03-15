package com.example.todo.controller.task;

public record TaskDTO(

        long id,

        String description,

        String summary,

        String status
) {
    // get/setメソッド

}
