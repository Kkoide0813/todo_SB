package com.example.todo.service.task;

import com.example.todo.controller.task.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    public List<TaskDTO> find() {
        var task1 = new TaskDTO(
                1L,
                "Spring Boot を学ぶ",
                "TODO アプリケーションを作ってみる",
                "TODO"
        );
        var task2 = new TaskDTO(
                2L,
                "Spring Security を学ぶ",
                "ログイン機能を作ってみる",
                "TODO"
        );

        return List.of(task1,task2);
    }

}
