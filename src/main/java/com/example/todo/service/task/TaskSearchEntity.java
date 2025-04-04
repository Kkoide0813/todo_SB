package com.example.todo.service.task;

import java.util.List;

public record TaskSearchEntity(

        String summary,

        List<TaskStatus> status
) {
}
