package com.example.todo.service.task;

public record TaskEntity(

        Long id, // プリミティブ型longにはnull渡せないので、オブジェクト型Longに変更

        String summary,

        String description,

        TaskStatus status
) {
}